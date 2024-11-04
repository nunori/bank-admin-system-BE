package com.im.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;


@Getter
public class UserLoginReq {
    @NotBlank(message = "사번은 필수입니다.")
    @Size(min = 7, max = 7, message = "사번은 7자입니다")
    private String userNumber;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, message = "비밀번호는 8자리 이상입니다.")
    private String userPassword;
}
