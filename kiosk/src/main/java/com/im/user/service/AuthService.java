package com.im.user.service;

import com.im.user.dto.UserLoginReq;
import com.im.user.entity.User;
import com.im.user.repository.UserRepository;
import com.im.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String login(UserLoginReq loginReq) {
        User user = userRepository.findByUserNumber(loginReq.getUserNumber())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if(!passwordEncoder.matches(loginReq.getUserPassword(), user.getUserPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.generateToken(user.getUserName());
    }

    public String refreshAccessToken(String refreshToken) {
        return "newAccessToken";
    }

    public void logout(String userNumber) {

    }
}
