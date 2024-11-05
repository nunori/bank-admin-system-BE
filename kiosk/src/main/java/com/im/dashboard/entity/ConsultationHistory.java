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

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "KIOSK_ID", nullable = false)
    private Integer kioskId;

    @Enumerated(EnumType.STRING)
    @Column(name = "WD_DVCD", nullable = true)
    private WindowDvcd windowDvcd; // Enum 값: "01", "02", "03", "04", "05"

    @Column(name = "TICKET_STIME", nullable = true)
    private LocalDateTime ticketStime;

    @Column(name = "WD_ID", nullable = true)
    private Integer windowId;

    @Enumerated(EnumType.STRING)
    @Column(name = "CSNL_CD", nullable = true)
    private ConsultationCode consultationCode; // Enum 값: "00", "01", "02", "03"

    @Column(name = "CSNL_START_DT", nullable = true)
    private LocalDateTime csnlStartDt;

    @Column(name = "WAIT_TIME", nullable = true)
    private Integer waitTime;

    @Column(name = "CSNL_END_DT", nullable = true)
    private LocalDateTime csnlEndDt;

    @Column(name = "CSNL_TIME", nullable = true)
    private Integer csnlTime;
}