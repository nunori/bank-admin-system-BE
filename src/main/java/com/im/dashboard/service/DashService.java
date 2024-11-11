// DashService.java
package com.im.dashboard.service;

import com.im.dashboard.dto.*;
import com.im.dashboard.repository.DashRepository;
import com.im.dashboard.repository.SpotInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashService {

    private final DashRepository dashRepository;
    private final SpotInfoRepository spotInfoRepository;

    public Integer calculateCustomerCount(CustomerCountReq countReq) {
        Integer deptIdInt = Integer.parseInt(countReq.getDeptId());
        LocalDateTime startDateTime = countReq.getStartDate().atStartOfDay();
        LocalDateTime endDateTime = resolveEndDateTime(countReq.getPeriod(), countReq.getEndDate());
        return dashRepository.countCustomersByDeptIdAndDateRange(deptIdInt, startDateTime, endDateTime);
    }

    public List<Double> calculateAverageWaitTimeByPeriod(WaitTimeData waitTimeReq) {
        Integer deptIdInt = Integer.parseInt(waitTimeReq.getDeptId());
        switch (waitTimeReq.getPeriod().toLowerCase()) {
            case "day":
                return extractAvgWaitTimes(dashRepository.averageWaitTimeByHourForDay(deptIdInt, waitTimeReq.getDate()));
            case "month":
                return extractAvgWaitTimes(dashRepository.averageWaitTimeByHourForMonth(deptIdInt, waitTimeReq.getDate()));
            case "year":
                return extractAvgWaitTimes(dashRepository.averageWaitTimeByHourForYear(deptIdInt, waitTimeReq.getDate()));
            default:
                throw new IllegalArgumentException("Invalid period specified: " + waitTimeReq.getPeriod());
        }
    }

    private LocalDateTime resolveEndDateTime(String period, LocalDate endDate) {
        switch (period.toLowerCase()) {
            case "day": return endDate.atTime(23, 59, 59);
            case "month": return endDate.withDayOfMonth(endDate.lengthOfMonth()).atTime(23, 59, 59);
            case "year": return endDate.withMonth(12).withDayOfMonth(31).atTime(23, 59, 59);
            default: throw new IllegalArgumentException("Invalid period specified: " + period);
        }
    }

    private List<Double> extractAvgWaitTimes(List<Object[]> results) {
        List<Double> avgWaitTimes = new ArrayList<>();
        for (Object[] result : results) {
            avgWaitTimes.add(((BigDecimal) result[1]).doubleValue());
        }
        return avgWaitTimes;
    }

    public List<Map<String, String>> getSummaryData(SummaryRequest request) {
        Integer customerCount = dashRepository.getCustomerCount(request.getDeptId(), request.getStartDate(), request.getEndDate());

        // averageWaitTime이 null이면 0으로 설정
        Double averageWaitTime = dashRepository.getAverageWaitTime(request.getDeptId(), request.getStartDate(), request.getEndDate());
        averageWaitTime = (averageWaitTime != null) ? averageWaitTime : 0.0;

        Map<String, String> customerData = Map.of("title", "내점 고객 수", "value", customerCount + "명");
        Map<String, String> waitTimeData = Map.of("title", "대기 시간 평균", "value", averageWaitTime + "분");

        return List.of(customerData, waitTimeData);
    }

    public Map<String, Object> getCustomerDataByWeek(SummaryRequest request) {
        List<Object[]> results = dashRepository.findWeeklyCustomerData(request.getDeptId(), request.getStartDate(), request.getEndDate());
        List<String> labels = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
        List<Integer> data = new ArrayList<>(Collections.nCopies(7, 0));
        results.forEach(result -> data.set(((Integer) result[0]) - 1, ((Long) result[1]).intValue()));
        return Map.of("labels", labels, "data", data);
    }

    public List<Map<String, Object>> getAllBranches() {
        return spotInfoRepository.findAllDeptIdAndDeptName().stream()
                .map(row -> {
                    Map<String, Object> branch = new HashMap<>();
                    branch.put("dept_id", row[0]);
                    branch.put("dept_name", row[1]);
                    return branch;
                })
                .collect(Collectors.toList());
    }

    public Map<String, Object> getCustomerChartData(CustomerChartRequest request) {
        String period = request.getPeriod();
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        Integer deptId = Integer.parseInt(request.getDeptId());

        Map<String, Object> result = new HashMap<>();
        List<Object[]> data;

        switch (period.toLowerCase()) {
            case "day":
                data = dashRepository.getCustomerCountByHour(deptId, startDate);
                result.put("labels", getHourlyLabels());
                break;
            case "month":
                data = dashRepository.getCustomerCountByDay(deptId, startDate, endDate);
                result.put("labels", getMonthlyLabels(startDate));
                break;
            case "year":
                data = dashRepository.getCustomerCountByMonth(deptId, startDate, endDate);
                result.put("labels", getYearlyLabels(startDate, endDate));
                break;
            case "custom":
                // 커스텀 기간 처리 (startDate와 endDate를 기준으로 기간 내 데이터를 조회)
                data = dashRepository.getCustomerCountByCustomRange(deptId, startDate, endDate);
                result.put("labels", getCustomRangeLabels(startDate, endDate));
                break;
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }

        result.put("data", data.stream().map(record -> record[1]).toArray());
        return result;
    }

    private List<String> getHourlyLabels() {
        List<String> labels = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            labels.add(i + "시");
        }
        return labels;
    }

    private List<String> getMonthlyLabels(LocalDate startDate) {
        int daysInMonth = startDate.lengthOfMonth();
        List<String> labels = new ArrayList<>();
        for (int i = 1; i <= daysInMonth; i++) {
            labels.add(i + "일");
        }
        return labels;
    }

    private List<String> getYearlyLabels(LocalDate startDate, LocalDate endDate) {
        // 연도별 레이블 생성 (5년)
        int startYear = startDate.getYear();
        int endYear = endDate.getYear();
        List<String> labels = new ArrayList<>();
        for (int year = startYear; year <= endYear; year++) {
            labels.add(year + "년");
        }
        return labels;
    }

    private List<String> getCustomRangeLabels(LocalDate startDate, LocalDate endDate) {
        List<String> labels = new ArrayList<>();
        // 여기서 startDate와 endDate 사이의 날짜를 레이블로 추가
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            labels.add(currentDate.toString()); // 날짜 형식 예: "2024-11-01"
            currentDate = currentDate.plusDays(1);
        }
        return labels;
    }

}