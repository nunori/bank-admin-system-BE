// DashController.java
package com.im.dashboard.controller;

import com.im.dashboard.dto.*;
import com.im.dashboard.service.DashService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashController {

    private final DashService dashService;

    @PostMapping("/customers/count")
    public ResponseEntity<Integer> customerCount(@RequestBody CustomerCountReq countReq) {
        Integer customerCount = dashService.calculateCustomerCount(countReq);
        return ResponseEntity.ok(customerCount);
    }

    // 고객 차트 데이터 API
    @PostMapping("/customers/chart-data")
    public ResponseEntity<Map<String, Object>> getCustomerChartData(@RequestBody CustomerChartRequest request) {
        Map<String, Object> data = dashService.getCustomerChartData(request);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/customers/wait-time/avg")
    public ResponseEntity<List<Double>> customerWait(@RequestBody WaitTimeAvgByHourReq waitTimeReq) {
        List<Double> avgWaitTime = dashService.calculateAverageWaitTimeByPeriod(waitTimeReq);
        return ResponseEntity.ok(avgWaitTime);
    }

    @PostMapping("/summary")
    public ResponseEntity<List<Map<String, String>>> getSummaryData(@RequestBody SummaryRequest request) {
        List<Map<String, String>> summaryData = dashService.getSummaryData(request);
        return ResponseEntity.ok(summaryData);
    }

    @PostMapping("/customers/data")
    public ResponseEntity<Map<String, Object>> getCustomerData(@RequestBody SummaryRequest request) {
        Map<String, Object> data = dashService.getCustomerDataByWeek(request);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/branches")
    public ResponseEntity<List<Map<String, Object>>> getAllBranches() {
        List<Map<String, Object>> branches = dashService.getAllBranches();
        return ResponseEntity.ok(branches);
    }
}