package com.im.dashboard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class ConsultationHistoryId implements Serializable {
    @Column(name = "TICKET_ID")
    private Integer ticketId;  // DB에서는 INT 타입

    @Column(name = "CRDT")
    private LocalDate crdt;

    @Column(name = "DEPT_ID")
    private String deptId;

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (!(o instanceof ConsultationHistoryId)) return false;
        ConsultationHistoryId that = (ConsultationHistoryId) o;
        return Objects.equals(ticketId, that.ticketId) &&
                Objects.equals(crdt, that.crdt) &&
                Objects.equals(deptId, that.deptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, crdt, deptId);
    }
}