package com.im.kiosk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketButtonUpdateReq {

    private int deptId;
    private int ticketButtonId;
    private String buttonDescription;
    private int position;
    private boolean visible;
}
