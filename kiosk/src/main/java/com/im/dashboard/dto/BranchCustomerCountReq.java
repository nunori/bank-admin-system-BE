package com.im.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BranchCustomerCountReq {
    private String deptId;
    private LocalDate startDate;
    private LocalDate endDate;
}
