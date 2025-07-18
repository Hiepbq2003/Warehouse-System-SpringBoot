package org.clotheswarehouse_hsf.service.impl;

import java.util.Optional;

import org.clotheswarehouse_hsf.model.Product;
import org.clotheswarehouse_hsf.model.SalesOrderDetail;
import org.clotheswarehouse_hsf.model.SalesOrder;
import org.clotheswarehouse_hsf.model.Warehouse;
import org.clotheswarehouse_hsf.model.Inventory;
import org.clotheswarehouse_hsf.repository.InventoryRepository;
import org.clotheswarehouse_hsf.repository.SalesOrderRepository;
import org.clotheswarehouse_hsf.service.SalesOrderService;
import org.clotheswarehouse_hsf.utils.EmailUtil;
import org.clotheswarehouse_hsf.utils.QrGenerator;
import org.clotheswarehouse_hsf.utils.VietQrGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {
    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private SalesOrderRepository repository;

    private final int PAGE_SIZE = 10;

    @Override
    public long count() {
        return salesOrderRepository.count();
    }

    @Override
    public List<SalesOrder> findAll() {
        return repository.findAll();
    }

    @Override
    public SalesOrder findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<SalesOrder> findByUserId(Integer userId) {
        return repository.findAll().stream()
                .filter(o -> o.getCreatedBy() != null && o.getCreatedBy().getUserId().equals(userId))
                .toList();
    }

    @Override
    public SalesOrder save(SalesOrder order) {
        return repository.save(order);
    }

    @Override
    public List<SalesOrder> filterOrders(String customerName, String status, Integer userId, int page) {
        int offset = (page - 1) * PAGE_SIZE;
        String nameFilter = (customerName == null || customerName.trim().isEmpty()) ? null : customerName.trim().toLowerCase();
        String statusFilter = (status == null || status.trim().isEmpty()) ? null : status;

        return repository.findAll().stream()
                .filter(order -> nameFilter == null || order.getCustomerName().toLowerCase().contains(nameFilter))
                .filter(order -> statusFilter == null || order.getStatus().name().equalsIgnoreCase(statusFilter))
                .filter(order -> userId == null || (order.getCreatedBy() != null && order.getCreatedBy().getUserId().equals(userId)))
                .skip(offset)
                .limit(PAGE_SIZE)
                .toList();
    }

    @Override
    public int getTotalPages(String customerName, String status, Integer userId) {
        String nameFilter = (customerName == null || customerName.trim().isEmpty()) ? null : customerName.trim().toLowerCase();
        String statusFilter = (status == null || status.trim().isEmpty()) ? null : status;

        long count = repository.findAll().stream()
                .filter(order -> nameFilter == null || order.getCustomerName().toLowerCase().contains(nameFilter))
                .filter(order -> statusFilter == null || order.getStatus().name().equalsIgnoreCase(statusFilter))
                .filter(order -> userId == null || (order.getCreatedBy() != null && order.getCreatedBy().getUserId().equals(userId)))
                .count();

        return (int) Math.ceil((double) count / PAGE_SIZE);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void updateStatus(Integer id, SalesOrder.OrderStatus status) {
        SalesOrder order = repository.findById(id).orElseThrow();
        order.setStatus(status);
        repository.save(order);
    }

    @Override
    public Map<Integer, List<SalesOrder.OrderStatus>> getValidTransitionsForOrders() {
        List<SalesOrder> orders = repository.findAll();
        Map<Integer, List<SalesOrder.OrderStatus>> result = new HashMap<>();

        for (SalesOrder order : orders) {
            result.put(order.getSalesOrderId(), getNextValidStatuses(order.getStatus()));
        }

        return result;
    }

    private List<SalesOrder.OrderStatus> getNextValidStatuses(SalesOrder.OrderStatus current) {
        return switch (current) {
            case pending_stock_check ->
                    List.of(SalesOrder.OrderStatus.awaiting_shipment, SalesOrder.OrderStatus.cancelled);
            case awaiting_shipment ->
                    List.of(SalesOrder.OrderStatus.shipped, SalesOrder.OrderStatus.pending_stock_check, SalesOrder.OrderStatus.cancelled);
            case shipped ->
                    List.of(SalesOrder.OrderStatus.completed, SalesOrder.OrderStatus.awaiting_shipment, SalesOrder.OrderStatus.cancelled);
            case completed -> List.of(SalesOrder.OrderStatus.shipped);
            case cancelled -> List.of(SalesOrder.OrderStatus.pending_stock_check);
        };
    }

    @Override
    public String generateOrderCode() {
        Integer maxId = repository.findMaxId();
        if (maxId == null) {
            maxId = 0;
        }
        int nextId = maxId + 1;
        return String.format("SO%06d", nextId); // ví dụ: SO000001
    }

    @Override
    public void approveOrder(Integer id) {
        SalesOrder order = repository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
        if (order.getStatus() == SalesOrder.OrderStatus.pending_stock_check) {
            order.setStatus(SalesOrder.OrderStatus.awaiting_shipment);
            repository.save(order);
        } else {
            throw new RuntimeException("Đơn hàng không ở trạng thái chờ kiểm kho");
        }
    }

    @Override
    public Page<SalesOrder> findByStatus(String statusStr, int page) {
        SalesOrder.OrderStatus status;
        try {
            status = SalesOrder.OrderStatus.valueOf(statusStr);
        } catch (IllegalArgumentException | NullPointerException e) {
            return Page.empty();
        }

        if (page < 1) page = 1;

        PageRequest pageable = PageRequest.of(page - 1, PAGE_SIZE);
        return repository.findByStatus(status, pageable);
    }

    @Override
    public void markAsCompleted(Integer orderId) {
        SalesOrder order = findById(orderId);
        if (order == null) throw new RuntimeException("Đơn hàng không tồn tại");

        if (order.getStatus() != SalesOrder.OrderStatus.shipped)
            throw new RuntimeException("Chỉ đơn đã giao mới được xác nhận thanh toán");

        order.setStatus(SalesOrder.OrderStatus.completed);
        save(order);
    }

    @Override
    public void markAsShippedAndSendEmail(Integer orderId) {
        SalesOrder order = findById(orderId);
        if (order == null) throw new RuntimeException("Đơn hàng không tồn tại");

        order.setStatus(SalesOrder.OrderStatus.shipped);
        save(order);

        for (SalesOrderDetail detail : order.getOrderDetails()) {
            Product product = detail.getProduct();
            Warehouse warehouse = detail.getWarehouse();
            int qty = detail.getQuantityOrdered();

            Optional<Inventory> optInventory = inventoryRepository.findByProductAndWarehouse(product, warehouse);
            if (optInventory.isEmpty()) {
                throw new RuntimeException("Không tìm thấy tồn kho cho sản phẩm " + product.getProductName()
                        + " tại kho " + warehouse.getWarehouseName());
            }

            Inventory inventory = optInventory.get();
            int newQuantity = inventory.getQuantityOnHand() - qty;
            if (newQuantity < 0) {
                throw new RuntimeException("Không đủ hàng trong kho cho sản phẩm " + product.getProductName());
            }

            inventory.setQuantityOnHand(newQuantity);
            inventoryRepository.save(inventory);
        }
        String qrBase64 = "";
        try {
            qrBase64 = VietQrGenerator.generateVietQrBase64(
                    "970422",
                    "2038320826",
                    "BUI QUANG HIEP",
                    order.getOrderCode(),
                    order.getTotalOrderValue() != null ? order.getTotalOrderValue().longValue() : 0
            );
        } catch (Exception e) {
            qrBase64 = "";
        }

        if (order.getCustomerEmail() != null && !order.getCustomerEmail().isEmpty()) {
            StringBuilder content = new StringBuilder();

            content.append("<div style='font-family: Arial, sans-serif; font-size: 14px;'>")
                    .append("<p>Xin chào <strong>").append(order.getCustomerName()).append("</strong>,</p>")
                    .append("<p>Đơn hàng <strong>").append(order.getOrderCode()).append("</strong> của bạn đã được giao thành công.</p>")
                    .append("<p>Chi tiết đơn hàng như sau:</p>");

            content.append("<table style='width: 100%; border-collapse: collapse; margin-top: 10px;'>")
                    .append("<thead style='background-color: #f2f2f2;'>")
                    .append("<tr>")
                    .append("<th style='border: 1px solid #ccc; padding: 8px; text-align: left;'>Sản phẩm</th>")
                    .append("<th style='border: 1px solid #ccc; padding: 8px; text-align: right;'>Số lượng</th>")
                    .append("<th style='border: 1px solid #ccc; padding: 8px; text-align: right;'>Đơn giá</th>")
                    .append("<th style='border: 1px solid #ccc; padding: 8px; text-align: right;'>Thành tiền</th>")
                    .append("<th style='border: 1px solid #ccc; padding: 8px; text-align: left;'>Kho</th>")
                    .append("<th style='border: 1px solid #ccc; padding: 8px; text-align: left;'>Địa chỉ kho</th>")
                    .append("</tr>")
                    .append("</thead>")
                    .append("<tbody>");

            double total = 0;
            for (SalesOrderDetail detail : order.getOrderDetails()) {
                int qty = detail.getQuantityOrdered();
                double price = detail.getUnitSalePrice();
                double lineTotal = qty * price;
                total += lineTotal;

                Warehouse wh = detail.getWarehouse();

                content.append("<tr>")
                        .append("<td style='border: 1px solid #ccc; padding: 8px;'>").append(detail.getProduct().getProductName()).append("</td>")
                        .append("<td style='border: 1px solid #ccc; padding: 8px; text-align: right;'>").append(qty).append("</td>")
                        .append("<td style='border: 1px solid #ccc; padding: 8px; text-align: right;'>").append(String.format("%.2f", price)).append("</td>")
                        .append("<td style='border: 1px solid #ccc; padding: 8px; text-align: right;'>").append(String.format("%.2f", lineTotal)).append("</td>")
                        .append("<td style='border: 1px solid #ccc; padding: 8px;'>").append(wh != null ? wh.getWarehouseName() : "Không có").append("</td>")
                        .append("<td style='border: 1px solid #ccc; padding: 8px;'>").append(wh != null ? wh.getAddress() : "Không có").append("</td>")
                        .append("</tr>");
            }

            content.append("</tbody>")
                    .append("<tfoot>")
                    .append("<tr style='background-color: #f9f9f9;'>")
                    .append("<td colspan='3' style='border: 1px solid #ccc; padding: 8px; text-align: right;'><strong>Tổng tiền</strong></td>")
                    .append("<td colspan='3' style='border: 1px solid #ccc; padding: 8px; text-align: right;'><strong>").append(String.format("%.2f", total)).append("</strong></td>")
                    .append("</tr>")
                    .append("</tfoot>")
                    .append("</table>");

//            if (!qrBase64.isEmpty()) {
//                content.append("<div style='margin-top: 20px;'>")
//                        .append("<p>Quét mã QR để xem đơn hàng của bạn:</p>")
//                        .append("<img src='data:image/png;base64,")
//                        .append(qrBase64)
//                        .append("' style='width: 200px; height: 200px;'/>")
//                        .append("</div>");
//            }

            content.append("<p>Cảm ơn bạn đã mua hàng!</p>")
                    .append("</div>");

            emailUtil.sendEmail(order.getCustomerEmail(), "Thông báo đơn hàng đã giao", content.toString());
        }
    }
}
