package org.clotheswarehouse_hsf.controller.saleStaff;

import java.time.LocalDateTime;
import java.time.LocalDate;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.clotheswarehouse_hsf.model.*;
import org.clotheswarehouse_hsf.service.*;
import org.clotheswarehouse_hsf.utils.QrGenerator;
import org.clotheswarehouse_hsf.utils.VietQrGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/saleStaff/salesOrder")
@RequiredArgsConstructor
public class SalesOrderController {

    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private SalesOrderService salesOrderService;
    @Autowired
    private SalesOrderDetailService salesOrderDetailService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final ProductService productService;
    @Autowired
    private InventoryService inventoryService;

    private Map<SalesOrder.OrderStatus, String> getStatusDisplayNames() {
        Map<SalesOrder.OrderStatus, String> statusDisplayNames = new LinkedHashMap<>();
        statusDisplayNames.put(SalesOrder.OrderStatus.pending_stock_check, "Chờ kiểm tra kho");
        statusDisplayNames.put(SalesOrder.OrderStatus.awaiting_shipment, "Chờ giao hàng");
        statusDisplayNames.put(SalesOrder.OrderStatus.shipped, "Đã giao");
        statusDisplayNames.put(SalesOrder.OrderStatus.completed, "Hoàn thành");
        statusDisplayNames.put(SalesOrder.OrderStatus.cancelled, "Đã hủy");
        return statusDisplayNames;
    }

    @GetMapping("/salesOrderList")
    public String listOrders(
            @RequestParam(value = "action", required = false, defaultValue = "list") String action,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "customer", required = false) String customer,
            @RequestParam(value = "page", defaultValue = "1") int page,
            RedirectAttributes redirectAttributes,
            Model model,
            HttpSession session
    ) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn chưa đăng nhập!");
            return "redirect:/login";
        }

        Integer userId = currentUser.getUserId();

        if ("delete".equals(action) && id != null) {
            salesOrderService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa đơn hàng thành công.");
            return "redirect:/saleStaff/salesOrder/salesOrderList";
        }

        if ("edit".equals(action) && id != null) {
            SalesOrder order = salesOrderService.findById(id);
            if (order == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy đơn hàng để sửa.");
                return "redirect:/saleStaff/salesOrder/salesOrderList";
            }

            List<Warehouse> warehouses = warehouseService.findAll();
            List<SalesOrderDetail> orderDetails = salesOrderDetailService.findBySalesOrderId(id);

            Integer selectedWarehouseId = orderDetails.stream()
                    .filter(d -> d.getWarehouse() != null)
                    .findFirst()
                    .map(d -> d.getWarehouse().getWarehouseId())
                    .orElse(null);

            model.addAttribute("warehouses", warehouses);
            model.addAttribute("order", order);
            model.addAttribute("orderDetails", orderDetails);
            model.addAttribute("selectedWarehouseId", selectedWarehouseId);

            return "saleStaff/salesOrder/editSaleOrder";
        }


        if ("update-status".equals(action) && id != null && status != null) {
            try {
                salesOrderService.updateStatus(id, SalesOrder.OrderStatus.valueOf(status));
                redirectAttributes.addFlashAttribute("successMessage", "Cập nhật trạng thái thành công.");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Cập nhật trạng thái thất bại.");
            }
            return "redirect:/saleStaff/salesOrder/salesOrderList";
        }

        model.addAttribute("orders", salesOrderService.filterOrders(customer, status, userId, page));
        model.addAttribute("statusFilter", status);
        model.addAttribute("customerFilter", customer);
        model.addAttribute("userIdFilter", userId);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", salesOrderService.getTotalPages(customer, status, userId));
        model.addAttribute("salesStaff", Collections.singletonList(currentUser));
        model.addAttribute("statusDisplayNames", getStatusDisplayNames());
        model.addAttribute("orderValidStatuses", salesOrderService.getValidTransitionsForOrders());

        return "saleStaff/salesOrder/salesOrderList";
    }

    @GetMapping("/viewSalesOrder")
    public String viewSalesOrder(@RequestParam("id") int id, Model model) {
        SalesOrder order = salesOrderService.findById(id);
        if (order == null) {
            return "redirect:/saleStaff/salesOrder/salesOrderList";
        }

        model.addAttribute("statusDisplayNames", getStatusDisplayNames());
        model.addAttribute("order", order);
        model.addAttribute("orderDetailsWithProduct", salesOrderDetailService.findBySalesOrderId(id));
        model.addAttribute("totalOrderValue", order.getTotalOrderValue());

        try {
            long amount = order.getTotalOrderValue() != null ? order.getTotalOrderValue().longValue() : 0;

            String qrBase64 = VietQrGenerator.generateVietQrBase64(
                    "970422",                     // BIN MB Bank
                    "2038320826",                // STK nhận
                    "BUI QUANG HIEP",            // Tên chủ tài khoản (tùy chọn, để "" nếu không cần)
                    order.getOrderCode(),        // Nội dung mã đơn hàng
                    amount                       // Số tiền
            );

            model.addAttribute("qrBase64", qrBase64);
        } catch (Exception e) {
            model.addAttribute("qrBase64", null);
        }

        return "saleStaff/salesOrder/viewSalesOrder";
    }

    @PostMapping("/delete")
    public String deleteSalesOrder(@RequestParam Integer id,
                                   RedirectAttributes redirectAttributes) {
        try {
            salesOrderService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa đơn hàng thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa đơn hàng.");
        }
        return "redirect:/saleStaff/salesOrder/salesOrderList";
    }

    @GetMapping("/createSaleOrder")
    public String showCreateForm(
            @RequestParam(name = "selectedWarehouseId", required = false) Integer selectedWarehouseId,
            Model model,
            HttpSession session
    ) {
        List<Warehouse> warehouses = warehouseService.findAll();
        List<Product> products = productService.findActiveProducts();

        List<Inventory> inventories = new ArrayList<>();
        if (selectedWarehouseId != null) {
            Warehouse selectedWarehouse = warehouseService.getById(selectedWarehouseId);
            inventories = inventoryService.findByWarehouse(selectedWarehouse);
            model.addAttribute("selectedWarehouseId", selectedWarehouseId);
        }

        model.addAttribute("warehouses", warehouses);
        model.addAttribute("products", products);
        model.addAttribute("filteredInventories", inventories);
        model.addAttribute("orderCode", salesOrderService.generateOrderCode());

        return "saleStaff/salesOrder/createSaleOrder";
    }

    @PostMapping("/createSaleOrder")
    public String handleCreateOrder(
            @RequestParam String orderCode,
            @RequestParam String customerName,
            @RequestParam String orderDate,
            @RequestParam(required = false) String customerEmail,
            @RequestParam(required = false) String notes,
            @RequestParam(name = "orderDetails[].warehouseId", required = false) List<Integer> warehouseIds,
            @RequestParam(name = "orderDetails[].productId", required = false) List<Integer> productIds,
            @RequestParam(name = "orderDetails[].quantity", required = false) List<Integer> quantities,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        try {
            User currentUser = (User) session.getAttribute("user");
            if (currentUser == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Bạn chưa đăng nhập!");
                return "redirect:/login";
            }

            if (productIds == null || productIds.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng chọn ít nhất một sản phẩm.");
                return "redirect:/saleStaff/salesOrder/createSaleOrder";
            }

            SalesOrder order = new SalesOrder();
            order.setOrderCode(orderCode);
            order.setCustomerName(customerName);
            order.setCustomerEmail(customerEmail);
            order.setOrderDate(LocalDateTime.now());
            order.setNotes(notes);
            order.setStatus(SalesOrder.OrderStatus.pending_stock_check);
            order.setCreatedAt(LocalDateTime.now());
            order.setCreatedBy(currentUser);

            SalesOrder savedOrder = salesOrderService.save(order);

            for (int i = 0; i < productIds.size(); i++) {
                Integer quantity = quantities.get(i);
                if (quantity == null || quantity <= 0) continue;

                Product product = productService.getById(productIds.get(i));
                Warehouse warehouse = warehouseService.getById(warehouseIds.get(i));

                if (product == null || warehouse == null) continue;

                SalesOrderDetail detail = new SalesOrderDetail();
                detail.setSalesOrder(savedOrder);
                detail.setProduct(product);
                detail.setWarehouse(warehouse);
                detail.setQuantityOrdered(quantity);
                detail.setUnitSalePrice(product.getSalePrice());

                salesOrderDetailService.save(detail);
            }

            redirectAttributes.addFlashAttribute("successMessage", "Tạo đơn hàng thành công!");
            return "redirect:/saleStaff/salesOrder/salesOrderList";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi tạo đơn hàng: " + e.getMessage());
            return "redirect:/saleStaff/salesOrder/createSaleOrder";
        }
    }

    @GetMapping("/products")
    @ResponseBody
    public String loadProductsByWarehouse(
            @RequestParam("warehouseId") Integer warehouseId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "4") int size,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        Warehouse warehouse = warehouseService.getById(warehouseId);
        List<Inventory> inventories = inventoryService.findByWarehouse(warehouse);

        List<Inventory> availableInventories = inventories.stream()
                .filter(inv -> inv.getQuantityOnHand() != null && inv.getQuantityOnHand() > 0)
                .toList();

        if (keyword != null && !keyword.isBlank()) {
            String kwLower = keyword.toLowerCase();
            availableInventories = availableInventories.stream()
                    .filter(inv -> inv.getProduct().getProductName().toLowerCase().contains(kwLower))
                    .toList();
        }

        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, availableInventories.size());
        if (fromIndex >= availableInventories.size()) {
            fromIndex = 0;
            toIndex = Math.min(size, availableInventories.size());
        }

        List<Inventory> paged = availableInventories.subList(fromIndex, toIndex);

        StringBuilder html = new StringBuilder();
        html.append("<div class='card mt-3'>");
        html.append("<div class='card-header'><strong>Sản phẩm trong kho</strong></div>");
        html.append("<div class='card-body'>");
        html.append("<div class='table-responsive'><table class='table table-bordered'>");
        html.append("<thead><tr><th>Sản phẩm</th><th>Tồn kho</th><th>Trạng thái</th><th>Số lượng đặt</th></tr></thead><tbody>");

        for (Inventory inv : paged) {
            Product p = inv.getProduct();
            int maxQty = inv.getQuantityOnHand();

            html.append("<tr>");
            html.append("<td>").append(p.getProductName()).append("</td>");
            html.append("<td>").append(maxQty).append("</td>");
            html.append("<td>").append(inv.getStatus()).append("</td>");
            html.append("<td>");
            html.append("<input type='hidden' name='orderDetails[].warehouseId' value='").append(warehouse.getWarehouseId()).append("'/>");
            html.append("<input type='hidden' name='orderDetails[].productId' value='").append(p.getProductId()).append("'/>");
            html.append("<input type='number' class='form-control' name='orderDetails[].quantity' max='").append(maxQty).append("' value='0' min='0'/>");
            html.append("</td>");
            html.append("</tr>");
        }
        html.append("</tbody></table></div>");

        int totalPages = (int) Math.ceil((double) availableInventories.size() / size);
        html.append("<nav><ul class='pagination'>");
        for (int i = 1; i <= totalPages; i++) {
            html.append("<li class='page-item'><button type='button' class='page-link' onclick='loadPage(")
                    .append(i).append(", document.getElementById(\"searchProductInputExternal\").value)'>")
                    .append(i).append("</button></li>");
        }
        html.append("</ul></nav>");

        html.append("</div></div>");
        return html.toString();
    }

    @PostMapping("/update")
    public String updateSalesOrder(
            @RequestParam Integer salesOrderId,
            @RequestParam String customerName,
            @RequestParam String customerEmail,
            @RequestParam String orderDate,
            @RequestParam(required = false) String notes,

            @RequestParam(name = "orderDetails[].orderDetailId", required = false) List<Integer> orderDetailIds,
            @RequestParam(name = "orderDetails[].productId", required = false) List<Integer> productIds,
            @RequestParam(name = "orderDetails[].warehouseId", required = false) List<Integer> warehouseIds,
            @RequestParam(name = "orderDetails[].quantity", required = false) List<Integer> quantities,

            @RequestParam(name = "deletedOrderDetailIds", required = false) List<Integer> deletedDetailIds,

            RedirectAttributes redirectAttributes
    ) {
        try {
            SalesOrder order = salesOrderService.findById(salesOrderId);
            if (order == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy đơn hàng.");
                return "redirect:/saleStaff/salesOrder/salesOrderList";
            }

            order.setCustomerName(customerName);
            order.setCustomerEmail(customerEmail);
            order.setOrderDate(LocalDate.parse(orderDate).atStartOfDay());
            order.setNotes(notes);

            List<SalesOrderDetail> currentDetails = order.getOrderDetails();

            // Xóa chi tiết theo id (orphanRemoval)
            if (deletedDetailIds != null && !deletedDetailIds.isEmpty()) {
                Iterator<SalesOrderDetail> iterator = currentDetails.iterator();
                while (iterator.hasNext()) {
                    SalesOrderDetail detail = iterator.next();
                    if (detail.getOrderDetailId() != null && deletedDetailIds.contains(detail.getOrderDetailId())) {
                        iterator.remove();
                    }
                }
            }

            // Thêm / cập nhật chi tiết, cộng dồn số lượng nếu trùng product + warehouse
            if (productIds != null && !productIds.isEmpty()) {
                for (int i = 0; i < productIds.size(); i++) {
                    Integer detailId = (orderDetailIds != null && orderDetailIds.size() > i) ? orderDetailIds.get(i) : null;
                    Integer productId = productIds.get(i);
                    Integer warehouseId = warehouseIds.get(i);
                    Integer quantity = quantities.get(i);

                    if (quantity == null || quantity <= 0) continue;

                    Product product = productService.getById(productId);
                    Warehouse warehouse = warehouseService.getById(warehouseId);

                    SalesOrderDetail matchedDetail = null;

                    // Tìm dòng cũ theo orderDetailId (nếu có)
                    if (detailId != null) {
                        for (SalesOrderDetail d : currentDetails) {
                            if (d.getOrderDetailId() != null && d.getOrderDetailId().equals(detailId)) {
                                matchedDetail = d;
                                break;
                            }
                        }
                    }

                    // Nếu không có orderDetailId, tìm dòng trùng product + warehouse để cộng dồn số lượng
                    if (matchedDetail == null) {
                        for (SalesOrderDetail d : currentDetails) {
                            if (d.getProduct().getProductId().equals(productId) &&
                                    d.getWarehouse().getWarehouseId().equals(warehouseId)) {
                                matchedDetail = d;
                                break;
                            }
                        }
                    }

                    if (matchedDetail != null) {
                        matchedDetail.setQuantityOrdered(matchedDetail.getQuantityOrdered() + quantity);
                        matchedDetail.setUnitSalePrice(product.getSalePrice());
                    } else {
                        SalesOrderDetail newDetail = new SalesOrderDetail();
                        newDetail.setSalesOrder(order);
                        newDetail.setProduct(product);
                        newDetail.setWarehouse(warehouse);
                        newDetail.setQuantityOrdered(quantity);
                        newDetail.setUnitSalePrice(product.getSalePrice());
                        currentDetails.add(newDetail);
                    }
                }
            }

            salesOrderService.save(order);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật đơn hàng thành công.");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật: " + e.getMessage());
        }

        return "redirect:/saleStaff/salesOrder/salesOrderList?action=edit&id=" + salesOrderId;
    }


}
