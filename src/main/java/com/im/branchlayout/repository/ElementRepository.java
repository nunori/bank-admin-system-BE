package com.im.branchlayout.repository;

import com.im.branchlayout.entity.ElementInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ElementRepository extends JpaRepository<ElementInfo, Integer> {
    List<ElementInfo> findByDeptIdAndFloorNumber(Integer deptId, Integer floorNumber);


    @Query("SELECT COUNT(e) FROM ElementInfo e WHERE e.deptId = :deptId AND e.floorNumber = :floorNumber AND e.elementType = 'window'")
    Integer countWindowsByDeptIdAndFloorNumber(@Param("deptId") Integer deptId, @Param("floorNumber") Integer floorNumber);

}
