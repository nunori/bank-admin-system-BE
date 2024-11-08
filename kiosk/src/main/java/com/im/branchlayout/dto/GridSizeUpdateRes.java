package com.im.branchlayout.dto;

import com.im.branchlayout.entity.GridInfo;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GridSizeUpdateRes {
    private Integer deptId;
    private Integer floorNumber;
    private Integer width;
    private Integer height;
    private LocalDateTime updatedAt;

    // GridInfo 엔티티를 GridSizeRes DTO로 변환하는 정적 메서드
    public static GridSizeUpdateRes from(GridInfo gridInfo) {
        return GridSizeUpdateRes.builder()
                .deptId(gridInfo.getDeptId())
                .floorNumber(gridInfo.getFloorNumber())
                .width(gridInfo.getGridWidth())
                .height(gridInfo.getGridHeight())
                .updatedAt(gridInfo.getUpdatedAt())
                .build();
    }
}