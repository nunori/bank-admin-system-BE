package com.im.dashboard.service;

import com.im.dashboard.dto.BranchCustomerCountReq;
import com.im.dashboard.dto.CustomerCountReq;
import com.im.dashboard.exception.CustomException;
import com.im.dashboard.repository.DashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashService {

    private final DashRepository dashRepository;

    public Integer calculateCustomerCount(CustomerCountReq countReq) {
        try {
            Integer deptIdInt = Integer.parseInt(countReq.getDeptId());
            LocalDate startDate = countReq.getStartDate();
            LocalDate endDate = countReq.getEndDate();
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime;

            switch (countReq.getPeriod().toLowerCase()) {
                case "day":
                    endDateTime = endDate.atTime(23, 59, 59);
                    break;
                case "month":
                    endDateTime = endDate.withDayOfMonth(endDate.lengthOfMonth())
                            .atTime(23, 59, 59);
                    break;
                case "year":
                    endDateTime = endDate.withMonth(12).withDayOfMonth(31)
                            .atTime(23, 59, 59);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid period specified: " + countReq.getPeriod());
            }

            return dashRepository.countCustomersByDeptIdAndDateRange(deptIdInt, startDateTime, endDateTime);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid deptId format: " + countReq.getDeptId());
        }
    }

    public List<Double> calculateAverageWaitTimeByPeriod(String deptId, String period, LocalDate date) {
        try {
            // String을 Integer로 변환
            Integer deptIdInt = Integer.parseInt(deptId);

            switch (period.toLowerCase()) {
                case "day":
                    return calculateAverageWaitTimeByHourForDay(deptIdInt.toString(), date);
                case "month":
                    return calculateAverageWaitTimeByHourForMonth(deptIdInt.toString(), date);
                case "year":
                    return calculateAverageWaitTimeByHourForYear(deptIdInt.toString(), date);
                default:
                    throw new IllegalArgumentException("Invalid period specified: " + period);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid deptId format: " + deptId);
        }
    }

    private List<Double> calculateAverageWaitTimeByHourForDay(String deptId, LocalDate date) {
        try {
            Integer deptIdInt = Integer.parseInt(deptId);
            List<Object[]> results = dashRepository.averageWaitTimeByHourForDay(deptIdInt, date);
            return extractAvgWaitTimes(results);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid deptId format: " + deptId, e);
        }
    }

    private List<Double> calculateAverageWaitTimeByHourForMonth(String deptId, LocalDate date) {
        try {
            Integer deptIdInt = Integer.parseInt(deptId);
            List<Object[]> results = dashRepository.averageWaitTimeByHourForMonth(deptIdInt, date);
            return extractAvgWaitTimes(results);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid deptId format: " + deptId, e);
        }
    }

    private List<Double> calculateAverageWaitTimeByHourForYear(String deptId, LocalDate date) {
        try {
            Integer deptIdInt = Integer.parseInt(deptId);
            List<Object[]> results = dashRepository.averageWaitTimeByHourForYear(deptIdInt, date);
            return extractAvgWaitTimes(results);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid deptId format: " + deptId, e);
        }
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

    public Integer getBranchCustomerCount(BranchCustomerCountReq request) {
        try {
            // deptId String -> Integer 변환
            Integer deptIdInt = Integer.parseInt(request.getDeptId());

            // LocalDate -> LocalDateTime 변환
            LocalDateTime startDateTime = request.getStartDate().atStartOfDay();
            LocalDateTime endDateTime = request.getEndDate().atTime(23, 59, 59);

            return dashRepository.countByBranchAndDate(deptIdInt, startDateTime, endDateTime);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid deptId format: " + request.getDeptId(), e);
        }
    }

}
