package com.im.user.serviceImpl;

import com.im.user.dto.UserRegisterReq;
import com.im.user.entity.User;
import com.im.user.repository.UserRepository;
import com.im.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public void registerUser(UserRegisterReq requestDto) {
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
