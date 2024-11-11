package com.im.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryRequest {
    private String deptId;
    private LocalDate startDate;
    private LocalDate endDate;
}