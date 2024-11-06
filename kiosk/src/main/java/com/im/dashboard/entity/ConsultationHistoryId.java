package com.im.dashboard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class ConsultationHistoryId implements Serializable {
    @Column(name = "ticket_id")
    private Integer ticketId;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "dept_id")
    private Integer deptId;

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (!(o instanceof ConsultationHistoryId)) return false;
        ConsultationHistoryId that = (ConsultationHistoryId) o;
        return Objects.equals(ticketId, that.ticketId) &&
                Objects.equals(createDate, that.createDate) &&  // crdt -> createDate
                Objects.equals(deptId, that.deptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, createDate, deptId);  // crdt -> createDate
    }
}