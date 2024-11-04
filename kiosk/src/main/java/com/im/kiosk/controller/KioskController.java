package com.im.kiosk.controller;

import com.im.config.KioskConfig;
import com.im.kiosk.service.KioskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kiosk")
@RequiredArgsConstructor
public class KioskController {

//    private final KioskService kioskService;
//
//    @GetMapping("/config")
//    public ResponseEntity<KioskConfig> KioskConfigGet(@RequestParam String branchId) {
//        // 영업점 ID(branchId)를 사용해 DB에서 설정 정보 조회
//        KioskConfig config = kioskService.getConfigByBranchId(branchId);
//        return ResponseEntity.ok(config);
//    }
}