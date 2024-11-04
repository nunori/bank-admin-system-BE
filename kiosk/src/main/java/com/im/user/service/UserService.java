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

        String encodedPassword = passwordEncoder.encode(requestDto.getUserPassword());
        User user = User.builder()
                .userName(requestDto.getUserName())
                .deptId(requestDto.getDeptId())
                .userDvcd(requestDto.getUserDvcd())
                .userNumber(requestDto.getUserNumber())
                .userPassword(encodedPassword)
                .build();

        userRepository.save(user);
    }
}
