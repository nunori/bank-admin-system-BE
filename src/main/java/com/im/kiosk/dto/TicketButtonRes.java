package com.im.kiosk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketButtonRes {

    private int ticketButtonId;
    private String buttonName;
    private String buttonDescription;
    private int buttonPosition;
    private boolean visible;
}
