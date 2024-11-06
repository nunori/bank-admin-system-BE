package com.im.dashboard.entity;
import com.im.dashboard.enums.ConsultationCode;
import com.im.dashboard.enums.WindowDvcd;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultation_history")
@Getter
@Setter
public class ConsultationHistory {
    @EmbeddedId
    private ConsultationHistoryId id;

    @Column(name = "window_dvcd", nullable = false)  // 컬럼명 변경
    @Enumerated(EnumType.STRING)
    private WindowDvcd windowDvcd;

    @Column(name = "ticket_start_time", nullable = false)  // 컬럼명 변경
    private LocalDateTime ticketStartTime;  // 변수명도 일관성있게 변경

    @Column(name = "kiosk_id", nullable = false)  // 소문자로 변경
    private Integer kioskId;

    @Column(name = "user_number", nullable = false)  // user_id -> user_number로 변경
    private String userNumber;  // Long -> String으로 변경

    @Column(name = "window_id", nullable = false)  // 컬럼명 변경
    private Integer windowId;

    @Column(name = "consultation_status", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT '00'")  // 컬럼명 변경
    @Enumerated(EnumType.STRING)
    private ConsultationCode consultationStatus;  // 변수명도 더 명확하게 변경

    @Column(name = "consultation_start_time")  // 컬럼명 변경
    private LocalDateTime consultationStartTime;  // 변수명도 일관성있게 변경

    @Column(name = "consultation_end_time")  // 컬럼명 변경
    private LocalDateTime consultationEndTime;  // 변수명도 일관성있게 변경
}
