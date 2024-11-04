package com.im.kiosk.repository;

import com.im.kiosk.entity.Kiosk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KioskRepository extends JpaRepository<Kiosk, Long> {
}
