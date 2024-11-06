package com.im.dashboard.repository;

import com.im.dashboard.entity.ConsultationHistory;
import com.im.dashboard.entity.ConsultationHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DashRepository extends JpaRepository<ConsultationHistory, ConsultationHistoryId> {

    @Query("SELECT COUNT(ch) FROM ConsultationHistory ch " +
            "WHERE ch.id.deptId = :deptId " +
            "AND ch.id.createDate BETWEEN :startDate AND :endDate")
    int countCustomersByDeptIdAndDateRange(
            @Param("deptId") Integer deptId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    // 일별 대기 시간 평균
    @Query(value = "SELECT HOUR(ticket_start_time) as hour, " +
            "AVG(TIMESTAMPDIFF(MINUTE, ticket_start_time, consultation_start_time)) as avg_wait_time " +
            "FROM consultation_history " +
            "WHERE dept_id = :deptId AND DATE(create_date) = :date " +
            "GROUP BY hour " +
            "ORDER BY hour", nativeQuery = true)
    List<Object[]> averageWaitTimeByHourForDay(
            @Param("deptId") Integer deptId,
            @Param("date") LocalDate date);

    // 월별 대기 시간 평균
    @Query(value = "SELECT HOUR(ticket_start_time) as hour, " +
            "AVG(TIMESTAMPDIFF(MINUTE, ticket_start_time, consultation_start_time)) as avg_wait_time " +
            "FROM consultation_history " +
            "WHERE dept_id = :deptId " +
            "AND MONTH(create_date) = MONTH(:date) " +
            "AND YEAR(create_date) = YEAR(:date) " +
            "GROUP BY hour " +
            "ORDER BY hour", nativeQuery = true)
    List<Object[]> averageWaitTimeByHourForMonth(
            @Param("deptId") Integer deptId,
            @Param("date") LocalDate date);

    // 연별 대기 시간 평균
    @Query(value = "SELECT HOUR(ticket_start_time) as hour, " +
            "AVG(TIMESTAMPDIFF(MINUTE, ticket_start_time, consultation_start_time)) as avg_wait_time " +
            "FROM consultation_history " +
            "WHERE dept_id = :deptId " +
            "AND YEAR(create_date) = YEAR(:date) " +
            "GROUP BY hour " +
            "ORDER BY hour", nativeQuery = true)
    List<Object[]> averageWaitTimeByHourForYear(
            @Param("deptId") Integer deptId,
            @Param("date") LocalDate date);

    @Query("SELECT COUNT(ch) FROM ConsultationHistory ch " +
            "WHERE ch.id.deptId = :deptId AND ch.id.createDate BETWEEN :startDate AND :endDate")
    Integer countByBranchAndDate(
            @Param("deptId") Integer deptId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

}