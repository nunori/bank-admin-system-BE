package com.im.branchlayout.enums;

import lombok.Getter;

@Getter
public enum ElementType {
    window("창구"),
    wall("벽"),
    entrance("입구");

    private final String description;

    ElementType(String description) {
        this.description = description;
    }

}
