package org.clotheswarehouse_hsf.repository;

import org.clotheswarehouse_hsf.model.SalesOrder;
import org.clotheswarehouse_hsf.model.SalesOrder.OrderStatus;
import org.clotheswarehouse_hsf.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Integer> {

    List<SalesOrder> findByCreatedBy(User user);

    @Query("SELECT MAX(s.salesOrderId) FROM SalesOrder s")
    Integer findMaxId();

    Page<SalesOrder> findByStatus(OrderStatus status, Pageable pageable);
}
