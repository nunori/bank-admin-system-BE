package com.im.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaitTimeData {
    private String deptId;  // 부서 ID
    private String period;  // 기간 (day, month, year)
    private LocalDate date; // 날짜
    private Double avgWaitTime; // 평균 대기 시간
}