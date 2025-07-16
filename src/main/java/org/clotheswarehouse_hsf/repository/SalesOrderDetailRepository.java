package org.clotheswarehouse_hsf.repository;

import org.clotheswarehouse_hsf.model.SalesOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesOrderDetailRepository extends JpaRepository<SalesOrderDetail, Integer> {
    List<SalesOrderDetail> findBySalesOrder_SalesOrderId(Integer salesOrderId);
}
