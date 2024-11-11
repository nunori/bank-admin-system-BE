// CustomerChartRequest.java
package com.im.dashboard.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerChartRequest {
    private String deptId;       // 지점 ID
    private LocalDate startDate;  // 시작 날짜
    private LocalDate endDate;    // 종료 날짜
    private String period;        // 조회 단위 (일별, 월별, 연별)
}