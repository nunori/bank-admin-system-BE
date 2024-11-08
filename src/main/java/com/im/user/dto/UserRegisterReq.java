package com.im.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterReq {
    @NotBlank(message = "지점 ID는 필수입니다.")
    private Integer deptId;  // String -> Integer

    @NotBlank(message = "지점 코드는 필수입니다.")
    @Pattern(regexp = "^(01|02)$", message = "지점 코드는 01(본부) 또는 02(영업점)이어야 합니다.")
    private String deptCode; // 새로 추가

    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 255, message = "이름은 최대 255자입니다.")
    private String userName;

    @NotBlank(message = "업무 구분 코드는 필수입니다.")
    @Size(max = 255, message = "업무 구분 코드는 최대 255자입니다.")
    private String userDvcd;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String userPassword;

    @NotBlank(message = "행번은 필수입니다.")
    @Size(max = 255, message = "행번은 최대 255자입니다.")
    private String userNumber;
}