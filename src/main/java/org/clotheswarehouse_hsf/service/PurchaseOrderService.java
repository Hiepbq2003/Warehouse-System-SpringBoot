package org.clotheswarehouse_hsf.service;

import org.clotheswarehouse_hsf.model.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PurchaseOrderService {
    List<PurchaseOrder> findAll();

    Optional<PurchaseOrder> findById(Integer id);

    PurchaseOrder save(PurchaseOrder order);

    void deleteById(Integer id);

    Page<PurchaseOrder> filterAndPaginate(
            Integer requesterId,
            String requestCode,
            String status,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            Pageable pageable
    );

    Page<PurchaseOrder> filterAndPaginateByWarehouse(
            Integer warehouseId,
            Integer requesterId,
            String requestCode,
            String status,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            Pageable pageable
    );


    void approve(Integer id);

    void reject(Integer id);


}
