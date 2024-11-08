package com.im.branchlayout.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ElementUpdateReq {

    private Integer elementId;
    private Integer floorNumber;
    private String elementName;
    private String elementType;
    private Integer elementGridX;
    private Integer elementGridY;
    private Integer elementWidth;
    private Integer elementHeight;
    private String elementColor;

}
