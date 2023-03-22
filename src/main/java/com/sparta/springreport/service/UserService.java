package com.sparta.springreport.service;

import com.sparta.springreport.common.ApiException;
import com.sparta.springreport.common.ExceptionEnum;
import com.sparta.springreport.config.WebSecurityConfig;
import com.sparta.springreport.dto.LoginRequestDto;
import com.sparta.springreport.dto.MessageResponse;
import com.sparta.springreport.dto.SignupRequestDto;
import com.sparta.springreport.dto.StatusEnum;
import com.sparta.springreport.entity.User;
import com.sparta.springreport.entity.UserRoleEnum;
import com.sparta.springreport.jwt.JwtUtil;
import com.sparta.springreport.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    //순서 알아보기
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private static final String ADMIN_TOKEN = "dltmdqlsrhksflwkxhzms";

    @Transactional
    public MessageResponse signup(SignupRequestDto signupRequestDto){
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        Optional<User> found = userRepository.findByUsername(username);

        if (found.isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다");
    }
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }
        User user = new User(username, password, role);
        userRepository.save(user);
        return new MessageResponse(StatusEnum.OK);
    }

    @Transactional(readOnly = true)
    public MessageResponse login(LoginRequestDto requestDto, HttpServletResponse response) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new ApiException(ExceptionEnum.NOT_FOUND_USER)
        );
        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new ApiException(ExceptionEnum.NOT_FOUND_USER);
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return new MessageResponse(StatusEnum.OK);
    }

}
