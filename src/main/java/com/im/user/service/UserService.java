package com.im.user.service;

import com.im.user.dto.UserRegisterReq;
import com.im.user.entity.User;
import com.im.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(UserRegisterReq requestDto) {
        if(requestDto.getUserPassword() == null || requestDto.getUserPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        if(requestDto.getUserPassword().length() < 8) {
            throw new IllegalArgumentException("비밀번호는 8자리 이상이어야 합니다.");
        }

        if(userRepository.existsByUserNumber(requestDto.getUserNumber())) {
            throw new IllegalArgumentException("이 사번은 이미 사용중입니다.");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getUserPassword());
        User user = User.builder()
                .userName(requestDto.getUserName())
                .deptId(requestDto.getDeptId())
                .deptCode(requestDto.getDeptCode())  // 추가된 부분
                .userDvcd(requestDto.getUserDvcd())
                .userNumber(requestDto.getUserNumber())
                .userPassword(encodedPassword)
                .build();

        userRepository.save(user);
    }
}
