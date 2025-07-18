package org.clotheswarehouse_hsf.controller.manager;

import lombok.RequiredArgsConstructor;
import org.clotheswarehouse_hsf.model.StockInward;
import org.clotheswarehouse_hsf.model.enums.StockInwardStatus;
import org.clotheswarehouse_hsf.model.Supplier;
import org.clotheswarehouse_hsf.model.User;
import org.clotheswarehouse_hsf.service.StockInwardService;
import org.clotheswarehouse_hsf.service.SupplierService;
import org.clotheswarehouse_hsf.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manager/stock-inward")
@RequiredArgsConstructor
public class ManageStockInwardController {

    private final StockInwardService stockInwardService;
    private final SupplierService supplierService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) StockInwardStatus status,
            @RequestParam(required = false) String inwardCode,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) Integer supplierId,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        LocalDateTime start = (startDate != null && !startDate.isEmpty()) ? LocalDateTime.parse(startDate + "T00:00:00") : null;
        LocalDateTime end = (endDate != null && !endDate.isEmpty()) ? LocalDateTime.parse(endDate + "T23:59:59") : null;

        // Gọi hàm filter thêm userId và supplierId
        Page<StockInward> stockInwards = stockInwardService.filterByManager(status, inwardCode, start, end, userId, supplierId, pageable);

        model.addAttribute("statuses", StockInwardStatus.values());
        model.addAttribute("param", Map.of(
                "status", status != null ? status.name() : "",
                "inwardCode", inwardCode != null ? inwardCode : "",
                "startDate", startDate != null ? startDate : "",
                "endDate", endDate != null ? endDate : "",
                "userId", userId != null ? userId.toString() : "",
                "supplierId", supplierId != null ? supplierId.toString() : ""
        ));
        model.addAttribute("users", userService.getAllPurchasingStaff());
        model.addAttribute("suppliers", supplierService.findAll());
        model.addAttribute("stockInwards", stockInwards);

        return "manager/stockInward/manageStockInward";
    }

    @PostMapping("/approve")
    public String approveStockInward(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        StockInward stock = stockInwardService.findById(id).orElse(null);
        if (stock != null) {
            if (stock.getStatus() == StockInwardStatus.PENDING_CHECK || stock.getStatus() == StockInwardStatus.DRAFT) {
                stock.setStatus(StockInwardStatus.APPROVED);
                stockInwardService.save(stock);
                redirectAttributes.addFlashAttribute("success", "Phiếu nhập đã được duyệt.");
            } else {
                redirectAttributes.addFlashAttribute("error", "Phiếu nhập không ở trạng thái chờ duyệt.");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Phiếu nhập không tồn tại.");
        }
        return "redirect:/manager/stock-inward/list";
    }

    @PostMapping("/reject")
    public String rejectStockInward(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        StockInward stock = stockInwardService.findById(id).orElse(null);
        if (stock != null) {
            if (stock.getStatus() == StockInwardStatus.PENDING_CHECK) {
                stock.setStatus(StockInwardStatus.CANCELLED);
                stockInwardService.save(stock);
                redirectAttributes.addFlashAttribute("success", "Đã từ chối phiếu nhập.");
            } else {
                redirectAttributes.addFlashAttribute("error", "Phiếu nhập không ở trạng thái chờ duyệt.");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Phiếu nhập không tồn tại.");
        }
        return "redirect:/manager/stock-inward/list";
    }


}
