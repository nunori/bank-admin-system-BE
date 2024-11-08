package com.im.branchlayout.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class FloorCreateReq {

    private Integer deptId;
    private Integer floorNumber;
    private String floorName;

}
