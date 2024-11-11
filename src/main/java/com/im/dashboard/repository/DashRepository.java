package com.im.dashboard.repository;

import com.im.dashboard.dto.WaitTimeData;
import com.im.dashboard.entity.ConsultationHistory;
import com.im.dashboard.entity.ConsultationHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
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

    // 시간별 대기 시간 (09:00 ~ 18:00)
    @Query(value = "SELECT HOUR(ticket_start_time) AS hour, " +
            "AVG(TIMESTAMPDIFF(MINUTE, ticket_start_time, consultation_start_time)) AS avg_wait_time " +
            "FROM consultation_history " +
            "WHERE dept_id = :deptId " +
            "AND DATE(create_date) = :date " +
            "GROUP BY hour " +
            "HAVING hour BETWEEN 9 AND 18 " +
            "ORDER BY hour", nativeQuery = true)
    List<WaitTimeData> getAvgWaitTimeByHour(@Param("deptId") String deptId, @Param("date") LocalDate date);

    // 월별 대기 시간
    @Query(value = "SELECT MONTH(create_date) AS label, " +
            "AVG(TIMESTAMPDIFF(MINUTE, ticket_start_time, consultation_start_time)) AS avg_wait_time " +
            "FROM consultation_history " +
            "WHERE dept_id = :deptId " +
            "AND create_date BETWEEN :startDate AND :endDate " +
            "GROUP BY label " +
            "ORDER BY label", nativeQuery = true)
    List<WaitTimeData> getAvgWaitTimeByMonth(@Param("deptId") String deptId,
                                             @Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);

    // 일별 대기 시간
    @Query(value = "SELECT DAY(create_date) AS label, " +
            "AVG(TIMESTAMPDIFF(MINUTE, ticket_start_time, consultation_start_time)) AS avg_wait_time " +
            "FROM consultation_history " +
            "WHERE dept_id = :deptId " +
            "AND create_date BETWEEN :startDate AND :endDate " +
            "GROUP BY label " +
            "ORDER BY label", nativeQuery = true)
    List<WaitTimeData> getAvgWaitTimeByDay(@Param("deptId") String deptId,
                                           @Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);


    // 일별 대기 시간 평균
    @Query(value = """
        SELECT 
            DATE_FORMAT(ticket_start_time, '%H:00') as period,
            AVG(TIMESTAMPDIFF(MINUTE, ticket_start_time, consultation_start_time)) as avg_wait_time
        FROM consultation_history
        WHERE DATE(ticket_start_time) = :targetDate
        AND dept_id = :deptId
        AND HOUR(ticket_start_time) BETWEEN 9 AND 17
        AND HOUR(consultation_start_time) BETWEEN 9 AND 17
        AND DATE(ticket_start_time) = DATE(consultation_start_time)
        GROUP BY DATE_FORMAT(ticket_start_time, '%H:00')
        ORDER BY period
    """, nativeQuery = true)
    List<Object[]> findDailyAverageWaitTime(
            @Param("targetDate") LocalDate targetDate,
            @Param("deptId") String deptId
    );

    // 월별 평균 대기시간
    @Query(value = """
        SELECT 
            DATE_FORMAT(ticket_start_time, '%m') as period,
            AVG(
                CASE 
                    WHEN HOUR(ticket_start_time) BETWEEN 9 AND 17 
                    AND HOUR(consultation_start_time) BETWEEN 9 AND 17
                    AND DATE(ticket_start_time) = DATE(consultation_start_time)
                    THEN TIMESTAMPDIFF(MINUTE, ticket_start_time, consultation_start_time)
                END
            ) as avg_wait_time
        FROM consultation_history
        WHERE ticket_start_time BETWEEN :startDate AND :endDate
        AND dept_id = :deptId
        GROUP BY DATE_FORMAT(ticket_start_time, '%m')
        ORDER BY period
    """, nativeQuery = true)
    List<Object[]> findMonthlyAverageWaitTime(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("deptId") String deptId
    );

    // 연도별 평균 대기시간
    @Query(value = """
        SELECT 
            DATE_FORMAT(ticket_start_time, '%Y') as period,
            AVG(
                CASE 
                    WHEN HOUR(ticket_start_time) BETWEEN 9 AND 17 
                    AND HOUR(consultation_start_time) BETWEEN 9 AND 17
                    AND DATE(ticket_start_time) = DATE(consultation_start_time)
                    THEN TIMESTAMPDIFF(MINUTE, ticket_start_time, consultation_start_time)
                END
            ) as avg_wait_time
        FROM consultation_history
        WHERE YEAR(ticket_start_time) BETWEEN YEAR(:baseDate) - 4 AND YEAR(:baseDate)
        AND dept_id = :deptId
        GROUP BY DATE_FORMAT(ticket_start_time, '%Y')
        ORDER BY period
    """, nativeQuery = true)
    List<Object[]> findYearlyAverageWaitTime(
            @Param("baseDate") LocalDate baseDate,
            @Param("deptId") String deptId
    );

    @Query(value = """
        SELECT 
            DATE_FORMAT(ticket_start_time, '%Y-%m-%d') as period,
            AVG(
                CASE 
                    WHEN HOUR(ticket_start_time) BETWEEN 9 AND 17 
                    AND HOUR(consultation_start_time) BETWEEN 9 AND 17
                    AND DATE(ticket_start_time) = DATE(consultation_start_time)
                    THEN TIMESTAMPDIFF(MINUTE, ticket_start_time, consultation_start_time)
                END
            ) as avg_wait_time
        FROM consultation_history
        WHERE ticket_start_time BETWEEN :startDate AND :endDate
        AND dept_id = :deptId
        GROUP BY DATE_FORMAT(ticket_start_time, '%Y-%m-%d')
        ORDER BY period
    """, nativeQuery = true)
    List<Object[]> findCustomAverageWaitTime(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("deptId") String deptId
    );

    @Query(value = """
        SELECT DATE(ticket_start_time) as period, 
               COUNT(*) as customer_count
        FROM consultation_history
        WHERE ticket_start_time BETWEEN :startDate AND :endDate
        AND dept_id = :deptId
        GROUP BY DATE(ticket_start_time)
        ORDER BY period
    """, nativeQuery = true)
    List<Object[]> getCustomerCountByCustomRange(
            @Param("deptId") Integer deptId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

}