package com.im.user.service;

import com.im.user.dto.UserRegisterReq;

public interface UserService {
    void registerUser(UserRegisterReq requestDto);
}
