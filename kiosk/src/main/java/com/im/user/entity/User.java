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
    private Long userId;

    @Column(nullable = false, length = 10)
    private String deptId;

    @Column(nullable = false, length = 10)
    private String userName;

    @Column(nullable = true, length = 2)
    private String userDvcd;

    @Column(nullable = false, length = 255)
    private String userPassword;

    @Column(nullable = false, length = 7)
    private String userNumber;

    public void updatePassword(String encodedPassword) {
        this.userPassword = encodedPassword;
    }
}
