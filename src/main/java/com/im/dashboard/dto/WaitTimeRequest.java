package com.im.dashboard.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WaitTimeRequest {
    private String type; // day, month, year
    private LocalDateTime startDate; // 시작일
    private LocalDateTime endDate; // 종료일
    private String deptId; // 부서 ID
}