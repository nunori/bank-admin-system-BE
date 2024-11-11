package com.im.dashboard.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class WaitTimeDTO {
    private String period;
    @Builder.Default
    private Double avgWaitTime = 0.0;  // 기본값을 0.0으로 설정
}