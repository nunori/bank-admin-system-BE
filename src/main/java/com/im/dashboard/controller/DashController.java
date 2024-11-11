package com.im.dashboard.controller;

import com.im.dashboard.dto.*;
import com.im.dashboard.entity.SpotInfo;
import com.im.dashboard.repository.DashRepository;
import com.im.dashboard.service.DashService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    
    @PostMapping("/customers/wait-time/avg")
    public ResponseEntity<List<Double>> customerWait(@RequestBody WaitTimeAvgByHourReq waitTimeReq) {
        List<Double> avgWaitTime = dashService.calculateAverageWaitTimeByPeriod(waitTimeReq.getDeptId(), waitTimeReq.getPeriod(), waitTimeReq.getDate());
        return ResponseEntity.ok(avgWaitTime);
    }

    @PostMapping("/customers/count/date-range")
    public ResponseEntity<Integer> branchCustomerCount(@RequestBody BranchCustomerCountReq request) {
        Integer customersCount = dashService.getBranchCustomerCount(request);
        return ResponseEntity.ok(customersCount);
    }

    @GetMapping("/branches")
    public ResponseEntity<List<Map<String, Object>>> getAllBranches() {
        List<Map<String, Object>> branches = dashService.getAllBranches();
        return ResponseEntity.ok(branches);
    }

    @PostMapping("/summary")
    public ResponseEntity<List<Map<String, String>>> getSummaryData(@RequestBody SummaryRequest request) {
        List<Map<String, String>> summaryData = dashService.getSummaryData(request.getDeptId(), request.getStartDate(), request.getEndDate());
        return ResponseEntity.ok(summaryData);
    }

    @PostMapping("/customers/data")
    public ResponseEntity<Map<String, Object>> getCustomerData(@RequestBody SummaryRequest request) {
        Map<String, Object> data = dashService.getCustomerDataByWeek(request.getDeptId(), request.getStartDate(), request.getEndDate());
        return ResponseEntity.ok(data);
    }

}
