package com.im.kiosk.controller;

import com.im.kiosk.dto.TicketButtonCreateReq;
import com.im.kiosk.dto.TicketButtonUpdateReq;
import com.im.kiosk.entity.TicketLayoutInfo;
import com.im.kiosk.service.TicketLayoutInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kiosk/ticket-layout")
@RequiredArgsConstructor
public class TicketLayoutInfoController {

    private final TicketLayoutInfoService ticketLayoutInfoService;

    @GetMapping("/{deptId}")
    public ResponseEntity<List<TicketLayoutInfo>> buttonsGetByDeptId(@PathVariable int deptId) {
        List<TicketLayoutInfo> buttons = ticketLayoutInfoService.getButtonsByDeptId(deptId);
        return ResponseEntity.ok(buttons);
    }

    @PutMapping("/update")
    public ResponseEntity<String> buttonsUpdate(@RequestBody List<TicketButtonUpdateReq> request) {
        ticketLayoutInfoService.updateButtonSettings(request);
        return ResponseEntity.ok("Button settings updated successfully");
    }

    // 버튼 생성
    @PostMapping("/create")
    public ResponseEntity<TicketLayoutInfo> createButton(@RequestBody TicketButtonCreateReq createReq) {
        TicketLayoutInfo newButton = ticketLayoutInfoService.createButton(createReq);
        return ResponseEntity.ok(newButton);
    }

    // 버튼 삭제
    @DeleteMapping("/delete/{ticketButtonId}")
    public ResponseEntity<String> deleteButton(@PathVariable int ticketButtonId) {
        ticketLayoutInfoService.deleteButton(ticketButtonId);
        return ResponseEntity.ok("Button deleted successfully");
    }
}
