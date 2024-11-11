package com.im.dashboard.controller;

import com.im.dashboard.dto.WaitTimeAvgByHourReq;
import com.im.dashboard.dto.WaitTimeDTO;
import com.im.dashboard.dto.WaitTimeData;
import com.im.dashboard.dto.WaitTimeRequest;
import com.im.dashboard.service.WaitTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard/wait-time")
@RequiredArgsConstructor
public class WaitTimeController {

    @Autowired
    private WaitTimeService waitTimeService;

    @PostMapping("/average")
    public List<WaitTimeDTO> getAverageWaitTimes(@RequestBody WaitTimeRequest request) {
        return waitTimeService.getAverageWaitTimes(request);
    }
}