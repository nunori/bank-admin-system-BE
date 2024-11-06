package com.im.dashboard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "spot_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SpotInfo {
    @Id
    @Column(name = "dept_id", nullable = false)
    private Integer deptId;  // String -> Integer로 변경

    @Column(name = "dept_code", nullable = false)  // 새로 추가
    private String deptCode;

    @Column(name = "dept_name", nullable = false)
    private String deptName;

    @Column(name = "start_time")  // s_time -> start_time으로 변경
    private String startTime;  // LocalTime -> String으로 변경

    @Column(name = "end_time")  // e_time -> end_time으로 변경
    private String endTime;  // LocalTime -> String으로 변경
}