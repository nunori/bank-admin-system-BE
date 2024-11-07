package com.im.branchlayout.repository;

import com.im.branchlayout.dto.FloorGetRes;
import com.im.branchlayout.entity.FloorInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FloorRepository extends JpaRepository<FloorInfo, Integer> {
    List<FloorInfo> findByDeptId(Integer deptId);

    boolean existsByDeptIdAndFloorNumber(Integer deptId, Integer floorNumber);
}
