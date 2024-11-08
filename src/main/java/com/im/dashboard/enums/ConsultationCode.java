package com.im.dashboard.enums;

import lombok.Getter;

@Getter
public enum ConsultationCode {
    대기("00"),
    상담중("01"),
    상담종료("02"),
    취소종료("03");

    private final String code;

    ConsultationCode(String code) {
        this.code = code;
    }

}