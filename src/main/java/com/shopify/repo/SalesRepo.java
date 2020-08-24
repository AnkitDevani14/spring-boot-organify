package com.shopify.repo;

import com.shopify.model.Invoice;
import com.shopify.model.Sales;
import com.shopify.model.Product;
import com.shopify.model.SalesReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SalesRepo extends JpaRepository<Sales, Long> {

    @Query("SELECT SUM(p2.totalPrice) FROM Sales p2 ")
    Long countUser();



    @Query("select new com.shopify.model.SalesReport( p.productName, s.price, sum(s.quantity),sum(s.totalPrice)) from Sales s join Product p on p.id = s.product WHERE month(s.createDate) =?1 and year(s.createDate) =?2 GROUP BY s.product")
    List<SalesReport> salesReport(int month, int year);

    @Query("select e from Sales e where year(e.createDate) = ?1 and month(e.createDate) = ?2")
    List<Sales> getByYearAndMonth(int year, int month);
}
