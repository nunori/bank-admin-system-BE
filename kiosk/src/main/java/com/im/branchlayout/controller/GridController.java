package com.im.branchlayout.controller;

import com.im.branchlayout.dto.GridSizeGetReq;
import com.im.branchlayout.dto.GridSizeUpdateReq;
import com.im.branchlayout.dto.GridSizeUpdateRes;
import com.im.branchlayout.service.GridService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branch-layout/grid")
@RequiredArgsConstructor
public class GridController {

    private final GridService gridService;

    @PutMapping("/size")
    public ResponseEntity<GridSizeUpdateRes> updateGridSize(
            @Valid @RequestBody GridSizeUpdateReq request) {
        System.out.println("그리드 사이즈 PUT 요청");
        GridSizeUpdateRes response = gridService.updateGridSize(request.getDeptId(), request.getFloorNumber(), request.getWidth(), request.getHeight());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/size")
    public ResponseEntity<GridSizeUpdateRes> getGridSize(@RequestBody GridSizeGetReq request) {
        GridSizeUpdateRes response = gridService.getCurrentGridSize(request.getDeptId(), request.getFloorNumber());
        return ResponseEntity.ok(response);
    }

    // 선택적: 한 부서의 모든 층의 그리드 정보 조회
    @GetMapping("/size/all")
    public ResponseEntity<List<GridSizeUpdateRes>> getAllGridSizes(
            @RequestParam Integer deptId) {
        List<GridSizeUpdateRes> responses = gridService.getAllGridSizes(deptId);
        return ResponseEntity.ok(responses);
    }
}