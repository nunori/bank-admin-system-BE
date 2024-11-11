// DashController.java
package com.im.dashboard.controller;

import com.im.dashboard.dto.*;
import com.im.dashboard.service.DashService;
import com.im.dashboard.service.WaitTimeService;
import com.im.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashController {

    private final DashService dashService;
    private final WaitTimeService waitTimeService;

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

    @PostMapping("/average")
    public List<WaitTimeDTO> getAverageWaitTimes(@RequestBody WaitTimeRequest request) {
        return waitTimeService.getAverageWaitTimes(request);
    }
}