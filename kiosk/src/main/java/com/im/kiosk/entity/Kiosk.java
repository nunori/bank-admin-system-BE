package com.im.kiosk.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
//@Table(name = "kiosk_info")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Kiosk {

    @Id
    private Long kioskId;
}
