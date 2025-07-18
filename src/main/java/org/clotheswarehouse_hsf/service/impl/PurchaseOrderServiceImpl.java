package org.clotheswarehouse_hsf.service.impl;

import lombok.RequiredArgsConstructor;
import org.clotheswarehouse_hsf.model.PurchaseOrder;
import org.clotheswarehouse_hsf.model.PurchaseOrderDetail;
import org.clotheswarehouse_hsf.repository.PurchaseOrderRepository;
import org.clotheswarehouse_hsf.repository.SupplierRepository;
import org.clotheswarehouse_hsf.repository.UserRepository;
import org.clotheswarehouse_hsf.service.PurchaseOrderService;
import org.clotheswarehouse_hsf.utils.EmailUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.clotheswarehouse_hsf.model.Supplier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierRepository supplierRepository;
    private final EmailUtil emailUtil;

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
    @Override
    public void sendOrderEmailToSupplier(PurchaseOrder order) {
        Optional<Supplier> supplierOpt = supplierRepository.findById(order.getSupplierId());
        if (supplierOpt.isEmpty()) return;

        Supplier supplier = supplierOpt.get();
        String to = supplier.getEmail();
        String subject = "Yêu cầu đặt hàng #" + order.getRequestCode();

        StringBuilder content = new StringBuilder();
        content.append("<div style='font-family: Arial, sans-serif;'>")
                .append("<p>Kính gửi <strong>").append(supplier.getSupplierName()).append("</strong>,</p>")
                .append("<p>Chúng tôi gửi đến quý công ty phiếu yêu cầu đặt hàng <strong>#")
                .append(order.getRequestCode()).append("</strong>.</p>");

        // Bảng sản phẩm
        content.append("<table border='1' cellpadding='8' cellspacing='0' style='border-collapse: collapse; width: 100%;'>")
                .append("<thead><tr style='background-color: #f2f2f2;'>")
                .append("<th>Sản phẩm</th>")
                .append("<th>Số lượng</th>")
                .append("<th>Đơn giá dự kiến</th>")
                .append("<th>Thành tiền</th>")
                .append("</tr></thead>")
                .append("<tbody>");

        double total = 0.0;
        for (PurchaseOrderDetail detail : order.getOrderDetails()) {
            String productName = detail.getProduct() != null ? detail.getProduct().getProductName() : "Không xác định";
            int quantity = detail.getRequestedQuantity() != null ? detail.getRequestedQuantity() : 0;
            double unitPrice = detail.getUnitPriceEstimated() != null ? detail.getUnitPriceEstimated().doubleValue() : 0.0;
            double subtotal = quantity * unitPrice;
            total += subtotal;

            content.append("<tr>")
                    .append("<td>").append(productName).append("</td>")
                    .append("<td align='center'>").append(quantity).append("</td>")
                    .append("<td align='right'>").append(String.format("%,.0f", unitPrice)).append(" đ</td>")
                    .append("<td align='right'>").append(String.format("%,.0f", subtotal)).append(" đ</td>")
                    .append("</tr>");
        }

        content.append("</tbody></table>");
        content.append("<p><strong>Tổng tiền dự kiến:</strong> ").append(String.format("%,.0f", total)).append(" đ</p>");

        content.append("<p>Ghi chú: ")
                .append(order.getNotes() != null ? order.getNotes() : "(Không có)").append("</p>");

        content.append("<br><p>Trân trọng,<br>Hệ thống Quản lý Kho Hàng</p>")
                .append("</div>");

        emailUtil.sendEmail(to, subject, content.toString());
    }
}
