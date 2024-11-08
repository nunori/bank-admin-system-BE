package com.im.branchlayout.controller;

import com.im.branchlayout.dto.FloorCreateReq;
import com.im.branchlayout.entity.FloorInfo;
import com.im.branchlayout.service.FloorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branch-layout/floors")
@RequiredArgsConstructor
public class FloorController {

    private final FloorService floorService;

    @GetMapping("/{deptId}")
    public ResponseEntity findAllByDeptId(@PathVariable("deptId") Integer deptId) {
        List<FloorInfo> floorInfos =  floorService.getFloorsByDeptId(deptId);
        return ResponseEntity.ok(floorInfos);
    }

    @PostMapping("/create")
    public ResponseEntity addFloor(@RequestBody FloorCreateReq request) {
        FloorInfo newFloor = floorService.addFloor(request);
        return ResponseEntity.ok(newFloor);
    }


}
