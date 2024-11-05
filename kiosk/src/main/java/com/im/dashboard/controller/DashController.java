package com.im.dashboard.controller;

import com.im.dashboard.dto.CustomerCountReq;
import com.im.dashboard.dto.WaitTimeAvgByHourReq;
import com.im.dashboard.service.DashService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    @PostMapping("/")
}
