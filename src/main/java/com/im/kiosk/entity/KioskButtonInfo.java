package com.im.kiosk.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "kiosk_button_info")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class KioskButtonInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "button_id")
    private Integer buttonId;

    @Column(name = "dept_id", nullable = false)
    private Integer deptId;

    @Column(name = "button_name", nullable = false)
    private String buttonName;

    @Column(name = "button_position", nullable = false)
    private int buttonPosition;

    @Column(name = "visible", nullable = false)
    private boolean visible;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    private String updatedBy;

    @PrePersist
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}