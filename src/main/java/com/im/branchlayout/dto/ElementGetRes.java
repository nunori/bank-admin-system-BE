package com.im.branchlayout.dto;

import com.im.branchlayout.enums.ElementType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ElementGetRes {

    private Integer elementId;
    private String elementName;
    private ElementType elementType;
    private String elementTypeDescription;
    private Integer elementGridX;
    private Integer elementGridY;
    private Integer elementWidth;
    private Integer elementHeight;
    private String elementColor;
}
