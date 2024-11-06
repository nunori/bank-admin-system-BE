package com.im.dashboard.repository;

import com.im.dashboard.entity.ConsultationHistory;
import com.im.dashboard.entity.ConsultationHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DashRepository extends JpaRepository<ConsultationHistory, ConsultationHistoryId> {

    @Query("SELECT COUNT(ch) FROM ConsultationHistory ch " +
            "WHERE ch.id.deptId = :deptId " +
            "AND ch.id.crdt BETWEEN :startDate AND :endDate")
    int countCustomersByDeptIdAndDateRange(
            @Param("deptId") String deptId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    // 일별 대기 시간 평균
    @Query(value = "SELECT HOUR(TICKET_STIME) AS hour, AVG(TIMESTAMPDIFF(MINUTE, TICKET_STIME, CSNL_START_DT)) AS avg_wait_time " +
            "FROM consultation_history " +
            "WHERE DEPT_ID = :deptId AND DATE(TICKET_STIME) = :date " +
            "GROUP BY hour " +
            "ORDER BY hour", nativeQuery = true)
    List<Object[]> averageWaitTimeByHourForDay(@Param("deptId") String deptId,
                                               @Param("date") LocalDate date);

    // 월별 대기 시간 평균
    @Query(value = "SELECT HOUR(TICKET_STIME) AS hour, AVG(TIMESTAMPDIFF(MINUTE, TICKET_STIME, CSNL_START_DT)) AS avg_wait_time " +
            "FROM consultation_history " +
            "WHERE DEPT_ID = :deptId AND MONTH(TICKET_STIME) = MONTH(:date) AND YEAR(TICKET_STIME) = YEAR(:date) " +
            "GROUP BY hour " +
            "ORDER BY hour", nativeQuery = true)
    List<Object[]> averageWaitTimeByHourForMonth(@Param("deptId") String deptId,
                                                 @Param("date") LocalDate date);

    // 연별 대기 시간 평균
    @Query(value = "SELECT HOUR(TICKET_STIME) AS hour, AVG(TIMESTAMPDIFF(MINUTE, TICKET_STIME, CSNL_START_DT)) AS avg_wait_time " +
            "FROM consultation_history " +
            "WHERE DEPT_ID = :deptId AND YEAR(TICKET_STIME) = YEAR(:date) " +
            "GROUP BY hour " +
            "ORDER BY hour", nativeQuery = true)
    List<Object[]> averageWaitTimeByHourForYear(@Param("deptId") String deptId,
                                                @Param("date") LocalDate date);

    @Query("SELECT COUNT(ch) FROM ConsultationHistory ch " +
            "WHERE ch.id.deptId = :deptId AND ch.id.crdt BETWEEN :startDate AND :endDate")
    Integer countByBranchAndDate(@Param("deptId") String deptId,
                                 @Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate);
}