package com.im.kiosk.controller;

import com.im.kiosk.dto.KioskButtonCreateReq;
import com.im.kiosk.dto.KioskButtonRes;
import com.im.kiosk.dto.KioskButtonUpdateReq;
import com.im.kiosk.service.KioskButtonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kiosk/buttons")
@RequiredArgsConstructor
public class KioskButtonController {

    private final KioskButtonService kioskButtonService;

    @GetMapping("/{deptId}")
    public ResponseEntity<List<KioskButtonRes>> getButtons(@PathVariable int deptId) {
        List<KioskButtonRes> buttons = kioskButtonService.getButtonsByDeptId(deptId);
        return ResponseEntity.ok(buttons);
    }

    @PostMapping("/create")
    public ResponseEntity<KioskButtonRes> createButton(@RequestBody KioskButtonCreateReq createReq) {
        KioskButtonRes newButton = kioskButtonService.createButton(createReq);
        return ResponseEntity.ok(newButton);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateButtons(@RequestBody List<KioskButtonUpdateReq> request) {
        kioskButtonService.updateButtonSettings(request);
        return ResponseEntity.ok("Button settings updated successfully");
    }

    @DeleteMapping("/delete/{buttonId}")
    public ResponseEntity<String> deleteButton(@PathVariable int buttonId) {
        kioskButtonService.deleteButton(buttonId);
        return ResponseEntity.ok("Button deleted successfully");
    }
}
