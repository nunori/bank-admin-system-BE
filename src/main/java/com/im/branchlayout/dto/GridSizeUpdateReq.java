package com.im.branchlayout.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GridSizeUpdateReq {
    @NotNull(message = "가로 크기는 필수입니다")
    @Min(value = 5, message = "최소 그리드 크기는 5입니다")
    @Max(value = 100, message = "최대 그리드 크기는 100입니다")
    private Integer width;

    @NotNull(message = "세로 크기는 필수입니다")
    @Min(value = 5, message = "최소 그리드 크기는 5입니다")
    @Max(value = 100, message = "최대 그리드 크기는 100입니다")
    private Integer height;

    private Integer deptId;
    private Integer floorNumber;
}