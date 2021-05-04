package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Profile("dev")
@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {


    @Query(value = "SELECT DATE(order_date)" +
            "     , SUM(total) AS daily_total" +
            "  FROM orders WHERE processed_by_id = ?1" +
            " GROUP BY DATE(order_date)", nativeQuery = true)
    List<Object[]> findWorkerBalanceHistory(long worker_id);

    @Query(value = "SELECT COUNT(*) FROM orders WHERE DATE(order_date) = ?1 ", nativeQuery = true)
    Integer countByOrderDate_Date(LocalDate date);

    @Query(value = "SELECT COUNT(*) FROM orders WHERE extract(year from order_date) =?1 and extract(month from order_date) =?2 ", nativeQuery = true)
    Integer countByOrderDate_Month(int year, int month);

    @Query(value = "SELECT COUNT(*) FROM orders WHERE extract(year from order_date) =?1", nativeQuery = true)
    Integer countByOrderDate_Year(int year);

    @Query(value = "SELECT SUM(total) FROM orders WHERE DATE(order_date) =?1", nativeQuery = true)
    Double getTotalOfDay(LocalDate date);

    @Query(value = "SELECT SUM(total) FROM orders WHERE extract(year from order_date) =?1 and extract(month from order_date) =?2", nativeQuery = true)
    Double getTotalOfMonth(int year, int month);

    @Query(value = "SELECT SUM(total) FROM orders WHERE extract(year from order_date) =?1  ", nativeQuery = true)
    Double getTotalOfYear(int year);

    @Query(value = "SELECT extract(year from order_date) as order_year, " +
            "extract(month from order_date) as order_month, " +
            "SUM(total) AS monthly_total " +
            "FROM orders WHERE  extract(year from order_date) =?1 " +
            "GROUP BY order_year, order_month " +
            "ORDER BY monthly_total DESC", nativeQuery = true)
    List<Object[]> getAllMonthlyTotalsOrderByTotal(int year);

    @Query(value = "SELECT SUM(total) as hour_total, extract (hour from order_date) as order_hour "+
            "FROM orders "+
            "WHERE DATE(order_date) = ?1 "+
            "GROUP BY extract(hour from order_date)"+
            "ORDER BY hour_total DESC "+
            "LIMIT 1", nativeQuery = true)
    List<Object[]> getPeakHourForDate(LocalDate date);


}
