package com.im.branchlayout.dto;

import lombok.Data;

@Data
public class FloorUpdateReq {
    private Integer deptId;
    private Integer floorNumber;
    private String floorName;
}