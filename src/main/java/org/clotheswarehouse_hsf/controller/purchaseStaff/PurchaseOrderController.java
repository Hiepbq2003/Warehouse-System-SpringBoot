package org.clotheswarehouse_hsf.controller.purchaseStaff;

import lombok.RequiredArgsConstructor;
import org.clotheswarehouse_hsf.model.*;
import org.clotheswarehouse_hsf.repository.*;
import org.clotheswarehouse_hsf.service.*;
import org.springframework.data.domain.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.clotheswarehouse_hsf.utils.EmailUtil;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/purchaseStaff/purchaseOrder")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final EmailUtil emailUtil;
    private final SupplierService supplierService;
    private final UserRepository userRepository;
    private final WarehouseService warehouseService;
    private final PurchaseOrderService purchaseOrderService;

    private static final Map<String, String> STATUS_DISPLAY_NAMES = Map.of(
            "submitted", "Đã gửi",
            "approved", "Đã duyệt",
            "ordered", "Đã đặt hàng",
            "rejected", "Từ chối",
            "partially_received", "Nhận một phần",
            "received", "Đã nhận"
    );


    @GetMapping
    public String listOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer requesterId,
            @RequestParam(required = false) Integer warehouseId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Pageable pageable = PageRequest.of(page, 10);

        if ("pending_approval".equalsIgnoreCase(status)) {
            model.addAttribute("orders", Page.empty());
            model.addAttribute("totalPages", 0);
            model.addAttribute("currentPage", page);
            model.addAttribute("status", status);
            model.addAttribute("requesterId", requesterId);
            model.addAttribute("fromDate", fromDate);
            model.addAttribute("toDate", toDate);
            model.addAttribute("warehouseId", warehouseId);
            return "purchaseStaff/purchaseOrder/purchaseOrderList";
        }

        String finalStatus = (status != null && status.trim().isEmpty()) ? null : status;

        Page<PurchaseOrder> ordersPage = purchaseOrderService.filterAndPaginateByWarehouse(
                warehouseId,
                requesterId,
                null,
                finalStatus,
                fromDate != null ? fromDate.atStartOfDay() : null,
                toDate != null ? toDate.atTime(23, 59, 59) : null,
                pageable
        );

        model.addAttribute("statusList", Arrays.asList(
                 "approved", "ordered"
        ));
        model.addAttribute("statusDisplayNames", STATUS_DISPLAY_NAMES);
        model.addAttribute("orders", ordersPage);
        model.addAttribute("totalPages", ordersPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("status", status);
        model.addAttribute("requesterId", requesterId);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        model.addAttribute("warehouseId", warehouseId);

        return "purchaseStaff/purchaseOrder/purchaseOrderList";
    }

    @GetMapping("/form")
    public String viewOrEdit(@RequestParam("id") Integer id, Model model) {
        PurchaseOrder order = purchaseOrderService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phiếu yêu cầu với ID: " + id));

        Warehouse warehouse = warehouseService.getById(order.getWarehouseId());
        String requesterUsername = userRepository.findById(order.getRequesterId())
                .map(User::getUsername)
                .orElse("Không xác định");

        List<Supplier> suppliers = supplierService.getAll();

        model.addAttribute("order", order);
        model.addAttribute("warehouseName", warehouse.getWarehouseName());
        model.addAttribute("requesterUsername", requesterUsername);
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("statusDisplayNames", STATUS_DISPLAY_NAMES);

        return "purchaseStaff/purchaseOrder/purchaseOrderForm";
    }
    @PostMapping("/update")
    public String updateOrder(
            @ModelAttribute PurchaseOrder order,
            @RequestParam("action") String action,
            RedirectAttributes redirectAttributes) {
        try {
            PurchaseOrder existing = purchaseOrderService.findById(order.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phiếu yêu cầu"));

            existing.setSupplierId(order.getSupplierId());
            existing.setNotes(order.getNotes());

            if ("send".equals(action)) {
                existing.setStatus(PurchaseOrder.OrderStatus.ordered);
                purchaseOrderService.save(existing);

                purchaseOrderService.sendOrderEmailToSupplier(existing);

                redirectAttributes.addFlashAttribute("message", "Đã gửi đơn và thông báo cho nhà cung cấp.");
            } else {
                purchaseOrderService.save(existing);
                redirectAttributes.addFlashAttribute("message", "Lưu phiếu yêu cầu thành công.");
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }

        return "redirect:/purchaseStaff/purchaseOrder";
    }

}
