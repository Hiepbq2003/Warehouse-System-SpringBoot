package org.clotheswarehouse_hsf.controller.warehouseStaff;

import jakarta.servlet.http.HttpSession;
import org.clotheswarehouse_hsf.model.*;
import org.clotheswarehouse_hsf.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/manager/warehouse")
public class WarehouseStaffController {
    @Autowired
    private ProductService productService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private UserService userService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/order-request")
    public String showOrderRequestPage(
            @RequestParam(value = "warehouseId", required = false) Integer warehouseId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "success", required = false) String successMessage,
            Model model
    ) {
        if (warehouseId == null) {
            List<Warehouse> warehouses = warehouseService.findAll();
            model.addAttribute("warehouses", warehouses);
            return "wareStaff/PurchaseRequest/selectWarehouse";
        }

        Optional<Warehouse> optionalWarehouse = warehouseService.findById(warehouseId);
        if (optionalWarehouse.isEmpty()) {
            model.addAttribute("message", "Không tìm thấy kho.");
            return "wareStaff/PurchaseRequest/selectWarehouse";
        }

        page = Math.max(page, 1);

        Warehouse warehouse = optionalWarehouse.get();
        int pageSize = 10;
        int offset = (page - 1) * pageSize;

        List<Inventory> inventories = inventoryService.findByWarehouseIdAndProductName(warehouseId, "", offset, pageSize);
        int totalItems = inventoryService.countByWarehouseIdAndProductName(warehouseId, "");

        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        model.addAttribute("warehouseName", warehouse.getWarehouseName());
        model.addAttribute("inventories", inventories);
        model.addAttribute("warehouseId", warehouseId);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
        }

        return "wareStaff/PurchaseRequest/orderRequest";
    }

    @PostMapping("/order-request/create")
    public String handleCreateOrderRequest(
            @RequestParam("warehouseId") Integer warehouseId,
            @RequestParam Map<String, String> allParams,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Bạn chưa đăng nhập.");
            return "redirect:/login";
        }

        Map<Integer, Integer> requestedQuantities = new HashMap<>();
        allParams.forEach((key, value) -> {
            if (key.startsWith("requestedQuantities[")) {
                try {
                    Integer productId = Integer.parseInt(key.substring(key.indexOf("[") + 1, key.indexOf("]")));
                    Integer quantity = Integer.parseInt(value);
                    if (quantity > 0) {
                        requestedQuantities.put(productId, quantity);
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        });

        if (requestedQuantities.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng nhập số lượng > 0.");
            redirectAttributes.addAttribute("warehouseId", warehouseId);
            return "redirect:/manager/warehouse/order-request";
        }

        PurchaseOrder order = new PurchaseOrder();
        order.setRequestCode("REQ-" + System.currentTimeMillis());
        order.setWarehouseId(warehouseId);
        order.setRequesterId(user.getUserId());
        order.setRequestDate(LocalDateTime.now());
        order.setStatus(PurchaseOrder.OrderStatus.pending_approval);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        String notes = allParams.get("notes");
        if (notes != null && !notes.trim().isEmpty()) {
            order.setNotes(notes.trim());
        }

        List<PurchaseOrderDetail> details = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : requestedQuantities.entrySet()) {
            Integer productId = entry.getKey();
            Integer quantity = entry.getValue();

            Product product = productService.getById(productId);
            if (product == null) {
                throw new RuntimeException("Không tìm thấy sản phẩm ID: " + productId);
            }

            Float purchasePrice = product.getPurchasePrice();
            if (purchasePrice == null) purchasePrice = 0f;

            BigDecimal estimatedPrice = BigDecimal.valueOf(purchasePrice)
                    .multiply(BigDecimal.valueOf(quantity));

            PurchaseOrderDetail detail = new PurchaseOrderDetail();
            detail.setProductId(productId);
            detail.setRequestedQuantity(quantity);
            detail.setUnitPriceEstimated(estimatedPrice);
            detail.setPurchaseOrder(order);
            details.add(detail);
        }
        order.setOrderDetails(details);
        purchaseOrderService.save(order);

        redirectAttributes.addFlashAttribute("message", "Gửi yêu cầu thành công!");
        redirectAttributes.addAttribute("warehouseId", warehouseId);
        return "redirect:/manager/inventory?productName=&warehouseId=&stockStatus=";
    }


}
