package com.im.user.controller;

import com.im.user.dto.UserLoginReq;
import com.im.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginReq loginReq) {
        Map<String, Object> response = authService.login(loginReq); // 토큰 및 deptId 반환
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAccessToken(@RequestBody String refreshToken) {
        try {
            String newAccessToken = authService.refreshAccessToken(refreshToken);
            return ResponseEntity.ok(newAccessToken);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam String userNumber) {
        authService.logout(userNumber);
        return ResponseEntity.ok("로그아웃이 성공적으로 처리되었습니다.");
    }
}
