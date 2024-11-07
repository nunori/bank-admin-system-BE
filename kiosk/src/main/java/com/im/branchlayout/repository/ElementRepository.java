package com.im.branchlayout.repository;

import com.im.branchlayout.entity.ElementInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElementRepository extends JpaRepository<ElementInfo, Integer> {
    List<ElementInfo> findByDeptIdAndFloorNumber(Integer deptId, Integer floorNumber);


}
