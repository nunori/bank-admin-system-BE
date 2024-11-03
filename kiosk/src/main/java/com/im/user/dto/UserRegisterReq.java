package com.im.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserRegisterReq {
    private String userId;
    private String deptId;
    private String userNm;
    private String userDvcd;
    private String userPassword;
}
