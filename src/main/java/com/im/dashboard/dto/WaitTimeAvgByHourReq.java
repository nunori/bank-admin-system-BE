package com.im.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaitTimeAvgByHourReq {
    private String deptId;
    private LocalDate date;
    private String period;



}
