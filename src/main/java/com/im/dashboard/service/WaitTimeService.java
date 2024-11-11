package com.im.dashboard.service;

import com.im.dashboard.dto.WaitTimeDTO;
import com.im.dashboard.dto.WaitTimeRequest;
import com.im.dashboard.repository.DashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class WaitTimeService {

    private final DashRepository dashRepository;

    public List<WaitTimeDTO> getAverageWaitTimes(WaitTimeRequest request) {
        System.out.println("Request Parameters - Type: " + request.getType() +
                ", StartDate: " + request.getStartDate() +
                ", EndDate: " + request.getEndDate() +
                ", DeptId: " + request.getDeptId());
        List<Object[]> results = switch (request.getType().toUpperCase()) {
            case "DAY" -> dashRepository
                    .findDailyAverageWaitTime(request.getStartDate().toLocalDate(), request.getDeptId());
            case "MONTH" -> dashRepository
                    .findMonthlyAverageWaitTime(request.getStartDate(), request.getEndDate(), request.getDeptId());
            case "YEAR" -> dashRepository
                    .findYearlyAverageWaitTime(request.getStartDate().toLocalDate(), request.getDeptId());
            case "CUSTOM" -> dashRepository
                    .findCustomAverageWaitTime(request.getStartDate(), request.getEndDate(), request.getDeptId());  // CUSTOM 처리 추가
            default -> throw new IllegalArgumentException("Invalid type: " + request.getType());
        };

        List<WaitTimeDTO> dtoList = results.stream()
                .map(result -> WaitTimeDTO.builder()
                        .period(result[0].toString())
                        .avgWaitTime(result[1] != null ? ((Number) result[1]).doubleValue() : 0.0)
                        .build())
                .collect(Collectors.toList());

        return fillEmptyPeriods(dtoList, request.getType(), request.getStartDate().toLocalDate());
    }

    private List<WaitTimeDTO> fillEmptyPeriods(List<WaitTimeDTO> data, String type, LocalDate baseDate) {
        Map<String, Double> dataMap = data.stream()
                .collect(Collectors.toMap(
                        WaitTimeDTO::getPeriod,
                        WaitTimeDTO::getAvgWaitTime
                ));

        List<WaitTimeDTO> result = new ArrayList<>();

        switch (type.toUpperCase()) {
            case "DAILY" -> {
                // 09:00부터 17:00까지의 시간대
                for (int hour = 9; hour <= 17; hour++) {
                    String period = String.format("%02d:00", hour);
                    result.add(WaitTimeDTO.builder()
                            .period(period)
                            .avgWaitTime(dataMap.getOrDefault(period, 0.0))
                            .build());
                }
            }
            case "MONTHLY" -> {
                // 1월부터 12월까지
                for (int month = 1; month <= 12; month++) {
                    String period = String.format("%02d", month);
                    result.add(WaitTimeDTO.builder()
                            .period(period)
                            .avgWaitTime(dataMap.getOrDefault(period, 0.0))
                            .build());
                }
            }
            case "YEARLY" -> {
                // 최근 5년
                int currentYear = baseDate.getYear();
                IntStream.rangeClosed(currentYear - 4, currentYear)
                        .forEach(year -> {
                            String period = String.valueOf(year);
                            result.add(WaitTimeDTO.builder()
                                    .period(period)
                                    .avgWaitTime(dataMap.getOrDefault(period, 0.0))
                                    .build());
                        });
            }
        }

        return result;
    }

}
