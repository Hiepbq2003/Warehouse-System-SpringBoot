package org.clotheswarehouse_hsf.controller.manager;

import lombok.RequiredArgsConstructor;
import org.clotheswarehouse_hsf.model.PurchaseOrder;
import org.clotheswarehouse_hsf.service.PurchaseOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/manager/purchase-orders")
@RequiredArgsConstructor
public class ManagePurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    @GetMapping
    public String listOrders(
            Model model,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String requesterId,
            @RequestParam(defaultValue = "") String requestCode,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "") String fromDate,
            @RequestParam(defaultValue = "") String toDate
    ) {
        int size = 10;
        LocalDateTime fromDateTime = null;
        LocalDateTime toDateTime = null;

        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (!fromDate.isBlank()) {
                fromDateTime = LocalDate.parse(fromDate, dateFormatter).atStartOfDay(); // 00:00:00
            }
            if (!toDate.isBlank()) {
                toDateTime = LocalDate.parse(toDate, dateFormatter).atTime(23, 59, 59); // 23:59:59
            }
        } catch (Exception ignored) {
        }

        Page<PurchaseOrder> orders = purchaseOrderService.filterAndPaginate(
                requesterId.isBlank() ? null : Integer.parseInt(requesterId),
                requestCode,
                status,
                fromDateTime,
                toDateTime,
                PageRequest.of(page - 1, size)
        );

        model.addAttribute("orders", orders.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orders.getTotalPages());

        model.addAttribute("requesterId", requesterId);
        model.addAttribute("requestCode", requestCode);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);

        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("pending_approval", "Chờ duyệt");
        statusMap.put("approved", "Đã duyệt");
        statusMap.put("rejected", "Từ chối");
        statusMap.put("ordered", "Đã đặt hàng");
        statusMap.put("partially_received", "Nhận một phần");
        statusMap.put("received", "Đã nhận");
        model.addAttribute("statusMap", statusMap);

        return "manager/purchaseOrder/managePurchaseOrder";
    }

    @PostMapping("/{id}/approve")
    public String approveOrder(@PathVariable Integer id) {
        purchaseOrderService.approve(id);
        return "redirect:/manager/purchase-orders";
    }

    @PostMapping("/{id}/reject")
    public String rejectOrder(@PathVariable Integer id) {
        purchaseOrderService.reject(id);
        return "redirect:/manager/purchase-orders";
    }
    @GetMapping("/{id}")
    public String viewPurchaseOrder(@PathVariable Integer id, Model model) {
        PurchaseOrder order = purchaseOrderService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn mua hàng với ID: " + id));

        model.addAttribute("order", order);
        model.addAttribute("statusMap", getStatusDisplayMap());

        return "manager/purchaseOrder/viewPurchaseOrder";
    }

    private Map<String, String> getStatusDisplayMap() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("pending_approval", "Chờ duyệt");
        map.put("approved", "Đã duyệt");
        map.put("rejected", "Từ chối");
        map.put("ordered", "Đã đặt hàng");
        map.put("partially_received", "Nhận một phần");
        map.put("received", "Đã nhận");
        return map;
    }


}
