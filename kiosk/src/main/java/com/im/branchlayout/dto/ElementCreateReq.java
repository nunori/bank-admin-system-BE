package com.im.branchlayout.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElementCreateReq {

    private Integer deptId;
    private Integer floorNumber;
    private String elementName;
    private String elementType;
    private Integer elementGridX;
    private Integer elementGridY;
    private Integer elementWidth;
    private Integer elementHeight;
    private String elementColor;

}
