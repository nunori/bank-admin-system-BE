package com.im.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginRes {
    private String userName;
    private Integer deptId;  // String -> Integer
    private String deptCode; // 새로 추가
    private String dvcd;
    private String userNumber;
    private String accessToken;
    private String refreshToken;
}