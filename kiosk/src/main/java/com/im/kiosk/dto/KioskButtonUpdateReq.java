package com.im.kiosk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KioskButtonUpdateReq {
    private int deptId;
    private int buttonId;
    private int position;
    private boolean visible;
}
