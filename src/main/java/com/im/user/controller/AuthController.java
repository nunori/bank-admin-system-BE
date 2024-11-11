package com.im.user.controller;

import com.im.user.dto.UserLoginReq;
import com.im.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginReq loginReq) {  // @Valid 추가
        log.info("Login request received for user: {}", loginReq.getUserNumber());
        log.info("=== Login request received ===");
        log.info("Request URL: /api/auth/login");
        log.info("Request body: {}", loginReq);
        try {
            Map<String, Object> response = authService.login(loginReq);
            log.info("Login successful for user: {}", loginReq.getUserNumber());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Login failed for user: {}", loginReq.getUserNumber(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
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
