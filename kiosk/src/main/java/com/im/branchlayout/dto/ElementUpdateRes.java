package com.im.branchlayout.dto;

import com.im.branchlayout.enums.ElementType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElementUpdateRes {

    private Integer elementId;
    private Integer floorNumber;
    private String elementName;
    private ElementType elementType;
    private Integer elementGridX;
    private Integer elementGridY;
    private Integer elementWidth;
    private Integer elementHeight;
    private String elementColor;

}
