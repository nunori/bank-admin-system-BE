package com.im.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserRegisterReq {

    @NotBlank(message = "소속 코드는 필수입니다.")
    @Size(max = 10, message = "소속 코드는 최대 10자리입니다.")
    private String deptId;

    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 10, message = "이름은 최대 10자입니다.")
    private String userName;

    @Size(max = 2, message = "업무 구분 코드는 최대 2자입니다.")
    private String userDvcd;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String userPassword;

    @NotBlank(message = "행번은 필수입니다.")
    @Size(max = 7, message = "행번은 최대 7자입니다.")
    private String userNumber;

}
