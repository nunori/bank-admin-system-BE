package com.im.dashboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerCountReq {
    private String deptId;
    private String period;
    private LocalDate startDate;
    private LocalDate endDate;
}
