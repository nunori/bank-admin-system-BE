package com.im.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_info")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "dept_id", nullable = false)
    private Integer deptId;

    @Column(name = "dept_code", nullable = false, length = 255)
    private String deptCode;

    @Column(name = "user_name", nullable = false, length = 255)
    private String userName;

    @Column(name = "user_dvcd", nullable = false, length = 255)
    private String userDvcd;

    @Column(name = "user_password", nullable = false, length = 255)
    private String userPassword;

    @Column(name = "user_number", nullable = false, length = 255, unique = true)
    private String userNumber;

    public void updatePassword(String encodedPassword) {
        this.userPassword = encodedPassword;
    }
}