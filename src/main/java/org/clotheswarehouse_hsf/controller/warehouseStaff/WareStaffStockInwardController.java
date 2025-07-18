package org.clotheswarehouse_hsf.controller.warehouseStaff;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.clotheswarehouse_hsf.model.StockInward;
import org.clotheswarehouse_hsf.model.StockInwardDetail;
import org.clotheswarehouse_hsf.model.enums.StockInwardStatus;
import org.clotheswarehouse_hsf.service.StockInwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ware-staff/stock-inward")
public class WareStaffStockInwardController {

    @Autowired
    private StockInwardService stockInwardService;

    @GetMapping("/check-list")
    public String showApprovedList(Model model,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<StockInward> stockInwards = stockInwardService.pageByStatus(StockInwardStatus.APPROVED, pageable);

        model.addAttribute("stockInwards", stockInwards);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", stockInwards.getTotalPages());
        return "wareStaff/stockInward/checkList";
    }

    @GetMapping("/check/{id}")
    public String showCheckForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<StockInward> optional = stockInwardService.findById(id);
        if (optional.isEmpty() || optional.get().getStatus() != StockInwardStatus.APPROVED) {
            redirectAttributes.addFlashAttribute("error", "Phiếu không tồn tại hoặc đã được kiểm.");
            return "redirect:/ware-staff/stock-inward/check-list";
        }
        model.addAttribute("stock", optional.get());
        return "wareStaff/stockInward/checkForm";
    }

    @PostMapping("/check")
    public String submitCheckForm(@RequestParam("stockInwardId") Integer stockInwardId,
                                  @RequestParam("actualQuantities") List<Integer> actualQuantities,
                                  RedirectAttributes redirectAttributes) {
        Optional<StockInward> optional = stockInwardService.findById(stockInwardId);
        if (optional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy phiếu.");
            return "redirect:/ware-staff/stock-inward/check-list";
        }

        StockInward stock = optional.get();
        List<StockInwardDetail> details = stock.getDetails();

        if (actualQuantities.size() != details.size()) {
            redirectAttributes.addFlashAttribute("error", "Dữ liệu không hợp lệ.");
            return "redirect:/ware-staff/stock-inward/check/" + stockInwardId;
        }

        for (int i = 0; i < details.size(); i++) {
            details.get(i).setQuantityReceived(actualQuantities.get(i));
        }

        stock.setStatus(StockInwardStatus.COMPLETED);
        stockInwardService.save(stock);
        redirectAttributes.addFlashAttribute("success", "Đã hoàn tất kiểm hàng.");
        return "redirect:/ware-staff/stock-inward/check-list";
    }
}
