package com.im.kiosk.repository;

import com.im.kiosk.entity.KioskButtonInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KioskButtonRepository extends JpaRepository<KioskButtonInfo, Integer> {
    List<KioskButtonInfo> findByDeptIdOrderByButtonPosition(Integer deptId);
}
