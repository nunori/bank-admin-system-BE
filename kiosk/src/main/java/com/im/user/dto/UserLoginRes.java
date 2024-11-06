package com.im.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRes {
    private String userName;
    private String deptId;
    private String dvcd;
    private String userNumber;
    private String accessToken;
    private String refreshToken;
}