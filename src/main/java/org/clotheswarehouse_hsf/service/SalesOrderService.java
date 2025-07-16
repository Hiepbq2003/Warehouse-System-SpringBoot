package org.clotheswarehouse_hsf.service;

import org.clotheswarehouse_hsf.model.SalesOrder;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface SalesOrderService {
    List<SalesOrder> findAll();

    SalesOrder findById(Integer id);

    List<SalesOrder> findByUserId(Integer userId);

    SalesOrder save(SalesOrder order);

    List<SalesOrder> filterOrders(String customerName, String status, Integer userId, int page);

    int getTotalPages(String customerName, String status, Integer userId);

    void delete(Integer id);

    void updateStatus(Integer id, SalesOrder.OrderStatus status);

    Map<Integer, List<SalesOrder.OrderStatus>> getValidTransitionsForOrders();

    String generateOrderCode();

    void approveOrder(Integer id);

    Page<SalesOrder> findByStatus(String status, int page);

    void markAsShippedAndSendEmail(Integer orderId);

    void markAsCompleted(Integer orderId);
}
