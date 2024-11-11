package com.im.dashboard.service;

import com.im.dashboard.dto.BranchCustomerCountReq;
import com.im.dashboard.dto.BranchesRes;
import com.im.dashboard.dto.CustomerCountReq;
import com.im.dashboard.entity.SpotInfo;
import com.im.dashboard.exception.CustomException;
import com.im.dashboard.repository.DashRepository;
import com.im.dashboard.repository.SpotInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DashService {

    private final DashRepository dashRepository;
    private final SpotInfoRepository spotInfoRepository;

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

    public List<Map<String, Object>> getAllBranches() {
        List<Object[]> results = spotInfoRepository.findAllDeptIdAndDeptName();
        return results.stream().map(row -> {
            Map<String, Object> branch = new HashMap<>();
            branch.put("dept_id", row[0]);
            branch.put("dept_name", row[1]);
            return branch;
        }).toList();
    }

    public List<Map<String, String>> getSummaryData(String deptId, LocalDate startDate, LocalDate endDate) {
        Integer customerCount = dashRepository.getCustomerCount(deptId, startDate, endDate);
        Double averageWaitTime = dashRepository.getAverageWaitTime(deptId, startDate, endDate);

        List<Map<String, String>> summaryData = new ArrayList<>();

        Map<String, String> customerData = new HashMap<>();
        customerData.put("title", "내점 고객 수");
        customerData.put("value", customerCount + "명");
        summaryData.add(customerData);

        Map<String, String> waitTimeData = new HashMap<>();
        waitTimeData.put("title", "대기 시간 평균");
        waitTimeData.put("value", averageWaitTime + "분");
        summaryData.add(waitTimeData);

        return summaryData;
    }

    public Map<String, Object> getCustomerDataByWeek(String deptId, LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = dashRepository.findWeeklyCustomerData(deptId, startDate, endDate);

        // 요일 라벨 생성
        List<String> labels = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");

        // 결과 데이터를 매핑할 리스트
        List<Integer> data = new ArrayList<>(Collections.nCopies(7, 0));

        for (Object[] result : results) {
            int dayOfWeek = ((Integer) result[0]) - 1; // 요일 인덱스(1~7) - 1로 보정
            int count = ((Long) result[1]).intValue(); // 고객 수
            data.set(dayOfWeek, count);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("labels", labels);
        response.put("data", data);

        return response;
    }

}
