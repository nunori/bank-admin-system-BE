package com.im.dashboard.repository;

import com.im.dashboard.entity.SpotInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotInfoRepository extends JpaRepository<SpotInfo, Integer> {

    @Query(value = "SELECT dept_id, dept_name FROM spot_info", nativeQuery = true)
    List<Object[]> findAllDeptIdAndDeptName();
}
