package com.im.branchlayout.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grid_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GridInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grid_id", nullable = false)
    private Integer gridId;

    @Column(name = "dept_id", nullable = false)
    private Integer deptId;

    @Column(name = "floor_number", nullable = false)
    private Integer floorNumber;

    @Column(name = "grid_width", nullable = false)
    private Integer gridWidth;

    @Column(name = "grid_height", nullable = false)
    private Integer gridHeight;
}
