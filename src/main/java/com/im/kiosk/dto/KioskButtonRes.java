package com.im.kiosk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KioskButtonRes {
    private int buttonId;
    private String buttonName;
    private int buttonPosition;
    private boolean visible;
}
