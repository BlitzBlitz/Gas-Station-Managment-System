package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {


    @Query(value = "select cast(o.orderDate as LocalDate) as day1, SUM(o.total) as daily_total from Order o" +
            " where o.processedBy.id = :worker_id" +
            " group by day1")
    List<Object[]> findWorkerBalanceHistory(long worker_id);

    @Query(value = "SELECT count(o) FROM Order o WHERE cast(o.orderDate as LocalDate) = :date ")
    Integer countByOrderDate_Date(LocalDate date);

    @Query(value = "SELECT COUNT(o) FROM Order o WHERE YEAR(cast(o.orderDate as LocalDate)) = :year " +
            "and MONTH(cast(o.orderDate as LocalDate)) = :month ")
    Integer countByOrderDate_Month(int year, int month);

    @Query(value = "SELECT COUNT(o) FROM Order o  WHERE YEAR(cast(o.orderDate as LocalDate)) = :year")
    Integer countByOrderDate_Year(int year);

    @Query(value = "SELECT SUM(o.total) FROM Order o WHERE cast(o.orderDate as LocalDate) =?1")
    Double getTotalOfDay(LocalDate date);

    @Query(value = "SELECT SUM(o.total) FROM Order o WHERE YEAR(cast(o.orderDate as LocalDate)) = :year " +
            "and MONTH(cast(o.orderDate as LocalDate)) = :month")
    Double getTotalOfMonth(int year, int month);

    @Query(value = "SELECT SUM(o.total) FROM Order o WHERE YEAR(cast(o.orderDate as LocalDate)) = :year ")
    Double getTotalOfYear(int year);

    @Query(value = "SELECT YEAR(cast(o.orderDate as LocalDate)) as order_year, " +
            "MONTH(cast(o.orderDate as LocalDate)) as order_month, " +
            "SUM(o.total) AS monthly_total " +
            "FROM Order o WHERE YEAR(cast(o.orderDate as LocalDate)) = :year " +
            "GROUP BY order_year, order_month " +
            "ORDER BY monthly_total DESC")
    List<Object[]> getAllMonthlyTotalsOrderByTotal(int year);


    //TODO Select max (select sum())..
//    @Query(value = "SELECT SUM(o.total) as hour_total, HOUR(o.orderDate ) as order_hour "+
//            "FROM Order o "+
//            "WHERE cast(o.orderDate as LocalDate) = :date "+
//            "GROUP BY HOUR(o.orderDate )"+
//            "ORDER BY hour_total DESC "+
//            "LIMIT 1")
//    List<Object[]> getPeakHourForDate(LocalDate date);


}
