package com.im.dashboard.repository;

import com.im.dashboard.entity.ConsultationHistory;
import com.im.dashboard.entity.ConsultationHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DashRepository extends JpaRepository<ConsultationHistory, ConsultationHistoryId> {

    // 시간별 방문 고객 수 (일별)
    @Query(value = "SELECT HOUR(create_date) AS hour, COUNT(*) FROM consultation_history " +
            "WHERE dept_id = :deptId AND DATE(create_date) = :date " +
            "GROUP BY hour ORDER BY hour", nativeQuery = true)
    List<Object[]> getCustomerCountByHour(@Param("deptId") Integer deptId, @Param("date") LocalDate date);

    // 일별 방문 고객 수 (월별)
    @Query(value = "SELECT DAY(create_date) AS day, COUNT(*) FROM consultation_history " +
            "WHERE dept_id = :deptId AND create_date BETWEEN :startDate AND :endDate " +
            "GROUP BY day ORDER BY day", nativeQuery = true)
    List<Object[]> getCustomerCountByDay(@Param("deptId") Integer deptId,
                                         @Param("startDate") LocalDate startDate,
                                         @Param("endDate") LocalDate endDate);

    // 월별 방문 고객 수 (연별)
    @Query(value = "SELECT MONTH(create_date) AS month, COUNT(*) FROM consultation_history " +
            "WHERE dept_id = :deptId AND create_date BETWEEN :startDate AND :endDate " +
            "GROUP BY month ORDER BY month", nativeQuery = true)
    List<Object[]> getCustomerCountByMonth(@Param("deptId") Integer deptId,
                                           @Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT COUNT(*) FROM consultation_history " +
            "WHERE dept_id = :deptId AND create_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    int countCustomersByDeptIdAndDateRange(
            @Param("deptId") Integer deptId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT HOUR(ticket_start_time) AS hour, " +
            "AVG(TIMESTAMPDIFF(MINUTE, ticket_start_time, consultation_start_time)) AS avg_wait_time " +
            "FROM consultation_history WHERE dept_id = :deptId AND DATE(create_date) = :date GROUP BY hour ORDER BY hour",
            nativeQuery = true)
    List<Object[]> averageWaitTimeByHourForDay(@Param("deptId") Integer deptId, @Param("date") LocalDate date);

    @Query(value = "SELECT HOUR(ticket_start_time) AS hour, " +
            "AVG(TIMESTAMPDIFF(MINUTE, ticket_start_time, consultation_start_time)) AS avg_wait_time " +
            "FROM consultation_history WHERE dept_id = :deptId AND MONTH(create_date) = MONTH(:date) " +
            "AND YEAR(create_date) = YEAR(:date) GROUP BY hour ORDER BY hour",
            nativeQuery = true)
    List<Object[]> averageWaitTimeByHourForMonth(@Param("deptId") Integer deptId, @Param("date") LocalDate date);

    @Query(value = "SELECT HOUR(ticket_start_time) AS hour, " +
            "AVG(TIMESTAMPDIFF(MINUTE, ticket_start_time, consultation_start_time)) AS avg_wait_time " +
            "FROM consultation_history WHERE dept_id = :deptId AND YEAR(create_date) = YEAR(:date) " +
            "GROUP BY hour ORDER BY hour",
            nativeQuery = true)
    List<Object[]> averageWaitTimeByHourForYear(@Param("deptId") Integer deptId, @Param("date") LocalDate date);

    @Query(value = "SELECT COUNT(*) FROM consultation_history " +
            "WHERE dept_id = :deptId AND create_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    Integer getCustomerCount(@Param("deptId") String deptId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT AVG(TIMESTAMPDIFF(MINUTE, ticket_start_time, consultation_start_time)) " +
            "FROM consultation_history WHERE dept_id = :deptId AND create_date BETWEEN :startDate AND :endDate",
            nativeQuery = true)
    Double getAverageWaitTime(@Param("deptId") String deptId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT DAYOFWEEK(create_date) AS day_of_week, COUNT(*) AS customer_count " +
            "FROM consultation_history WHERE dept_id = :deptId AND create_date BETWEEN :startDate AND :endDate " +
            "GROUP BY day_of_week", nativeQuery = true)
    List<Object[]> findWeeklyCustomerData(@Param("deptId") String deptId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}