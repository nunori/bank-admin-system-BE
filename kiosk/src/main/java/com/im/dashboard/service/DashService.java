package com.im.dashboard.service;

import com.im.dashboard.dto.CustomerCountReq;
import com.im.dashboard.exception.CustomException;
import com.im.dashboard.repository.DashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashService {

    private final DashRepository dashRepository;

    public Integer calculateCustomerCount(CustomerCountReq countReq) {
        LocalDate startDate = countReq.getStartDate();
        LocalDate endDate = countReq.getEndDate();

        switch (countReq.getPeriod().toLowerCase()) {
            case "day":
                return dashRepository.countCustomersByDeptIdAndDateRange(countReq.getDeptId(), startDate, endDate);
            case "month":
                return dashRepository.countCustomersByDeptIdAndDateRange(countReq.getDeptId(), startDate.withDayOfMonth(1), endDate.withDayOfMonth(endDate.lengthOfMonth()));
            case "year":
                return dashRepository.countCustomersByDeptIdAndDateRange(countReq.getDeptId(), startDate.withDayOfYear(1), endDate.withMonth(12).withDayOfMonth(31));
            default:
                throw new IllegalArgumentException("Invalid period specified: " + countReq.getPeriod());
        }
    }

    public List<Double> calculateAverageWaitTimeByPeriod(String deptId, String period, LocalDate date) {
        switch (period.toLowerCase()) {
            case "day":
                return calculateAverageWaitTimeByHourForDay(deptId, date);
            case "month":
                return calculateAverageWaitTimeByHourForMonth(deptId, date);
            case "year":
                return calculateAverageWaitTimeByHourForYear(deptId, date);
            default:
                throw new IllegalArgumentException("Invalid period specified: " + period);
        }
    }

    private List<Double> calculateAverageWaitTimeByHourForDay(String deptId, LocalDate date) {
        List<Object[]> results = dashRepository.averageWaitTimeByHourForDay(deptId, date);
        return extractAvgWaitTimes(results);
    }

    private List<Double> calculateAverageWaitTimeByHourForMonth(String deptId, LocalDate date) {
        List<Object[]> results = dashRepository.averageWaitTimeByHourForMonth(deptId, date);
        return extractAvgWaitTimes(results);
    }

    private List<Double> calculateAverageWaitTimeByHourForYear(String deptId, LocalDate date) {
        List<Object[]> results = dashRepository.averageWaitTimeByHourForYear(deptId, date);
        return extractAvgWaitTimes(results);
    }

    private List<Double> extractAvgWaitTimes(List<Object[]> results) {
        List<Double> avgWaitTimes = new ArrayList<>();
        for (Object[] result : results) {
            // 첫 번째 요소는 시간, 두 번째 요소는 평균 대기 시간
            if (result[1] instanceof BigDecimal) {
                BigDecimal avgWaitTimeBigDecimal = (BigDecimal) result[1]; // 평균 대기 시간
                avgWaitTimes.add(avgWaitTimeBigDecimal.doubleValue()); // BigDecimal을 Double로 변환
            } else if (result[1] instanceof Double) {
                avgWaitTimes.add((Double) result[1]); // Double인 경우 바로 추가
            } else {
                // 예상하지 못한 타입 처리 (필요시 예외 처리 추가)
                throw new CustomException("Unexpected type for average wait time: " + result[1].getClass().getName());
            }
        }
        return avgWaitTimes;
    }

}
