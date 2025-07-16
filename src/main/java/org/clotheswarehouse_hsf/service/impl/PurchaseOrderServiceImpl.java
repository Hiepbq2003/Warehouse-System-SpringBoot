package org.clotheswarehouse_hsf.service.impl;

import lombok.RequiredArgsConstructor;
import org.clotheswarehouse_hsf.model.PurchaseOrder;
import org.clotheswarehouse_hsf.repository.PurchaseOrderRepository;
import org.clotheswarehouse_hsf.service.PurchaseOrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    @Override
    public List<PurchaseOrder> findAll() {
        return purchaseOrderRepository.findAll();
    }

    @Override
    public Optional<PurchaseOrder> findById(Integer id) {
        return purchaseOrderRepository.findById(id);
    }

    @Override
    public PurchaseOrder save(PurchaseOrder order) {
        return purchaseOrderRepository.save(order);
    }

    @Override
    public void deleteById(Integer id) {
        purchaseOrderRepository.deleteById(id);
    }

    @Override
    public Page<PurchaseOrder> filterAndPaginate(
            Integer requesterId,
            String requestCode,
            String status,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            Pageable pageable) {

        Specification<PurchaseOrder> spec = (root, query, cb) -> cb.conjunction();

        if (requesterId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("requesterId"), requesterId));
        }

        if (requestCode != null && !requestCode.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("requestCode")), "%" + requestCode.toLowerCase() + "%"));
        }

        if (status != null && !status.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("status"), status));
        }

        if (fromDate != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("createdAt"), fromDate));
        }

        if (toDate != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("createdAt"), toDate));
        }

        return purchaseOrderRepository.findAll(spec, pageable);
    }

    @Override
    public void approve(Integer id) {
        PurchaseOrder order = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với ID: " + id));
        order.setStatus(PurchaseOrder.OrderStatus.approved);
        order.setApprovedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        purchaseOrderRepository.save(order);
    }

    @Override
    public void reject(Integer id) {
        PurchaseOrder order = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với ID: " + id));
        order.setStatus(PurchaseOrder.OrderStatus.rejected);
        order.setUpdatedAt(LocalDateTime.now());
        order.setApprovedAt(LocalDateTime.now());
        purchaseOrderRepository.save(order);
    }
    @Override
    public Page<PurchaseOrder> filterAndPaginateByWarehouse(
            Integer warehouseId,
            Integer requesterId,
            String requestCode,
            String status,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            Pageable pageable
    ) {
        Specification<PurchaseOrder> spec = (root, query, cb) -> cb.conjunction();

        if (warehouseId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("warehouseId"), warehouseId));
        }

        if (requesterId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("requesterId"), requesterId));
        }
        if (requestCode != null && !requestCode.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("requestCode")), "%" + requestCode.toLowerCase() + "%"));
        }

        if (status != null && !status.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("status"), status));
        } else {
            spec = spec.and((root, query, cb) ->
                    cb.and(
                            cb.notEqual(root.get("status"), "pending_approval"),
                            cb.notEqual(root.get("status"), "rejected")
                    ));
        }
        if (fromDate != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("createdAt"), fromDate));
        }

        if (toDate != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("createdAt"), toDate));
        }

        return purchaseOrderRepository.findAll(spec, pageable);
    }

}
