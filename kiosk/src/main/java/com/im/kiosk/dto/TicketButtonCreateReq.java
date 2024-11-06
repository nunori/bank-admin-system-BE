package com.im.kiosk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketButtonCreateReq {
    private Integer deptId;
    private String buttonName;
    private int buttonPosition;
    private boolean visible;
    private String updatedBy;
}
