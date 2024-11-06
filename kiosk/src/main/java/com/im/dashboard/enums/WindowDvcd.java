package com.im.dashboard.enums;

import lombok.Getter;

@Getter
public enum WindowDvcd {
    일반업무("01"),
    대출업무("02"),
    대구로페이업무("03"),
    환전업무("04"),
    기업대출업무("05");

    private final String code;

    WindowDvcd(String code) {
        this.code = code;
    }

}