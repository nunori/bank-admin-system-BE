package com.im.branchlayout.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "grid_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 업데이트를 위해 Setter 추가 또는 @Setter 어노테이션 사용
    public void updateGridSize(Integer width, Integer height) {
        this.gridWidth = width;
        this.gridHeight = height;
    }

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}