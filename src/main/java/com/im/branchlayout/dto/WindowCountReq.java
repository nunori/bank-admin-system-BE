package com.im.branchlayout.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WindowCountReq {

    private Integer deptId;
    private Integer floorNumber;
}
