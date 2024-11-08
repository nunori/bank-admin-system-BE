package com.im.branchlayout.controller;

import com.im.branchlayout.dto.*;
import com.im.branchlayout.service.ElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branch-layout")
@RequiredArgsConstructor
public class ElementController {
    private final ElementService elementService;

    @PostMapping("/getElements")
    public ResponseEntity<List<ElementGetRes>> getElements(@RequestBody ElementGetReq request) {
        List<ElementGetRes> elements = elementService.getElementsByDeptAndFloor(request.getDeptId(), request.getFloorNumber());
        return new ResponseEntity<>(elements, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ElementCreateRes> createElement(@RequestBody ElementCreateReq request) {
        ElementCreateRes createElement = elementService.createElement(request);
        return new ResponseEntity<>(createElement, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{elementId}")
    public ResponseEntity<Void> deleteElement(@PathVariable Integer elementId) {
        elementService.deleteElement(elementId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<ElementUpdateRes> updateElement(@RequestBody ElementUpdateReq request) {
        ElementUpdateRes updatedElement = elementService.updateElement(request);
        return ResponseEntity.ok(updatedElement);
    }

}
