package com.im.branchlayout.dto;

import com.im.branchlayout.entity.ElementInfo;
import com.im.branchlayout.enums.ElementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElementCreateRes {

    private Integer elementId;
    private Integer deptId;
    private Integer floorNumber;
    private String elementName;
    private ElementType elementType;
    private String elementTypeDescription;
    private Integer elementGridX;
    private Integer elementGridY;
    private Integer elementWidth;
    private Integer elementHeight;
    private String elementColor;

    public ElementCreateRes(ElementInfo elementInfo) {
        this.elementId = elementInfo.getElementId();
        this.deptId = elementInfo.getDeptId();
        this.floorNumber = elementInfo.getFloorNumber();
        this.elementName = elementInfo.getElementName();
        this.elementType = elementInfo.getElementType();
        this.elementTypeDescription = elementInfo.getElementType().getDescription();
        this.elementGridX = elementInfo.getElementGridX();
        this.elementGridY = elementInfo.getElementGridY();
        this.elementWidth = elementInfo.getElementWidth();
        this.elementHeight = elementInfo.getElementHeight();
        this.elementColor = elementInfo.getElementColor();
    }
}
