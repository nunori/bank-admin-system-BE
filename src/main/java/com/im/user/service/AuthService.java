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
        User user = userRepository.findByUserNumber(loginReq.getUserNumber())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if(!passwordEncoder.matches(loginReq.getUserPassword(), user.getUserPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_DEPT_" + user.getDeptCode());  // deptId 대신 deptCode 사용
        claims.put("deptId", user.getDeptId());
        claims.put("deptCode", user.getDeptCode());

        String accessToken = jwtUtil.generateToken(user.getUserNumber(), claims);

        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("deptId", user.getDeptId());
        response.put("deptCode", user.getDeptCode());
        response.put("userName", user.getUserName());
        response.put("dvcd", user.getUserDvcd());
        response.put("userNumber", user.getUserNumber());

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
