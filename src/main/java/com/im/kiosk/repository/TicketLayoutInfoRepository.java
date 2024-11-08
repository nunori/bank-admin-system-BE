package com.im.kiosk.repository;

import com.im.kiosk.entity.TicketLayoutInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketLayoutInfoRepository extends JpaRepository<TicketLayoutInfo, Integer> {
    List<TicketLayoutInfo> findByDeptIdOrderByButtonPosition(Integer deptId);
}
