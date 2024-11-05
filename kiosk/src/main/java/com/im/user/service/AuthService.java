package com.im.user.service;

import com.im.user.dto.UserLoginReq;
import com.im.user.entity.User;
import com.im.user.repository.UserRepository;
import com.im.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Map<String, Object> login(UserLoginReq loginReq) {
        System.out.println("로그인 시도: " + loginReq.getUserNumber());
        User user = userRepository.findByUserNumber(loginReq.getUserNumber())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        System.out.println("FindByUserNumber: " + user);

        if(!passwordEncoder.matches(loginReq.getUserPassword(), user.getUserPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_DEPT_" + user.getDeptId());
        claims.put("deptId", user.getDeptId());
        System.out.println("user.getDeptId(): " + user.getDeptId());

        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }

        String token = jwtUtil.generateToken(user.getUserNumber(), claims);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("deptId", user.getDeptId());

        return response;
    }

    public String refreshAccessToken(String refreshToken) {
        String userNumber = jwtUtil.extractUserNumber(refreshToken);

        if(!jwtUtil.validateToken(refreshToken, userNumber)) {
            throw new RuntimeException("Invalid or expired refresh token");
        }

        User user = userRepository.findByUserNumber(userNumber)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_DEPT_" + user.getDeptId());

        return jwtUtil.generateToken(userNumber, claims);
    }

    public void logout(String userNumber) {

    }
}
