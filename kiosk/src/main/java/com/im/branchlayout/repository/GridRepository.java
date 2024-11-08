package com.im.branchlayout.repository;

import com.im.branchlayout.entity.GridInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GridRepository extends JpaRepository<GridInfo, Integer> {
    Optional<GridInfo> findByDeptIdAndFloorNumber(Integer deptId, Integer floorNumber);
    List<GridInfo> findAllByDeptId(Integer deptId);
}
