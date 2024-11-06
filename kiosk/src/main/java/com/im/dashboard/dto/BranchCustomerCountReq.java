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
    private String deptId;      // 프론트엔드와의 인터페이스를 위해 String으로 유지
    private LocalDate startDate;
    private LocalDate endDate;
}