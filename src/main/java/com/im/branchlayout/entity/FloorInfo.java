package com.im.branchlayout.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "floor_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FloorInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "floor_id", nullable = false)
    private Integer floorId;

    @Column(name = "dept_id", nullable = false)
    private Integer deptId;

    @Column(name = "floor_number", nullable = false)
    private Integer floorNumber;

    @Column(name = "floor_name")
    private String floorName;
}
