package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,String> {

    @Query(value = "SELECT DATE(order_date)" +
            "     , SUM(total) AS daily_total" +
            "  FROM orders WHERE worker_id = ?1" +
            " GROUP BY DATE(order_date)",  nativeQuery = true)
    List<Object[]> findWorkerBalanceHistory(long worker_id);

    @Query(value = "SELECT COUNT(*) FROM orders WHERE  DATE(order_date) =?1", nativeQuery = true)
    Integer countByOrderDate_Date(LocalDate date);

    @Query(value = "SELECT SUM(total) FROM orders WHERE DATE(order_date) =?1", nativeQuery = true)
    Double countTotalOfDay(LocalDate date);

    @Query(value = "SELECT SUM(total) FROM orders WHERE extract(year from order_date) =?1 and extract(month from order_date) =?2", nativeQuery = true)
    Object countTotalOfMonth(int year, int month);

    @Query(value = "SELECT SUM(total) FROM orders WHERE extract(year from order_date) =?1  ", nativeQuery = true)
    Object countTotalOfYear(int year);

    List<Object[]>  getAllMonthlyTotalsOrderByTotal(int year);
}
