package com.im.kiosk.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_layout_info")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TicketLayoutInfo {

    @Id
    @Column(name = "ticket_button_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketButtonId;

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

    @Column(name = "button_description")
    private String buttonDescription;

    private String updatedBy;

    @PrePersist
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
