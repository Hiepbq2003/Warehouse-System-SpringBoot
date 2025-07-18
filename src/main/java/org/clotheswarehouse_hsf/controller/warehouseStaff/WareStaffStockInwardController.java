package org.clotheswarehouse_hsf.controller.warehouseStaff;

import org.clotheswarehouse_hsf.model.Inventory;
import org.clotheswarehouse_hsf.service.InventoryService;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/ware-staff/stock-inward")
public class WareStaffStockInwardController {

    @Autowired
    private StockInwardService stockInwardService;
    @Autowired
    private InventoryService inventoryService;

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
        StockInward stock = stockInwardService.findById(id).orElse(null);

        if (stock == null || stock.getStatus() != StockInwardStatus.APPROVED) {
            redirectAttributes.addFlashAttribute("error", "Phiếu không tồn tại hoặc đã được kiểm.");
            return "redirect:/ware-staff/stock-inward/check-list";
        }

        Map<Integer, Inventory> inventoryMap = new HashMap<>();
        List<StockInwardDetail> details = new ArrayList<>(stock.getDetails());

        for (StockInwardDetail detail : details) {
            Integer productId = detail.getProduct().getProductId();
            Integer warehouseId = stock.getWarehouse().getWarehouseId();

            Inventory inventory = inventoryService
                    .findByProductIdAndWarehouseId(productId, warehouseId)
                    .orElse(null);

            if (inventory != null) {
                inventoryMap.put(productId, inventory);
            }
        }

        // Gán vào model sau khi xong mọi xử lý
        model.addAttribute("stock", stock);
        model.addAttribute("inventoryMap", inventoryMap);

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
            redirectAttributes.addFlashAttribute("error", "Số lượng kiểm không khớp với chi tiết phiếu.");
            return "redirect:/ware-staff/stock-inward/check/" + stockInwardId;
        }

        for (int i = 0; i < details.size(); i++) {
            StockInwardDetail detail = details.get(i);
            int quantity = actualQuantities.get(i);
            detail.setQuantityReceived(quantity);

            // Tìm tồn kho hiện có
            Inventory inventory = inventoryService.findByProductAndWarehouse(
                    detail.getProduct(), stock.getWarehouse()
            ).orElseGet(() -> Inventory.builder()
                    .product(detail.getProduct())
                    .warehouse(stock.getWarehouse())
                    .quantityOnHand(0)
                    .build());

            inventory.setQuantityOnHand(inventory.getQuantityOnHand() + quantity);
            inventory.setLastUpdated(new Timestamp(System.currentTimeMillis()));
            inventoryService.save(inventory);
        }

        stock.setInwardDate(LocalDateTime.now());
        stock.setStatus(StockInwardStatus.COMPLETED);
        stockInwardService.save(stock);

        redirectAttributes.addFlashAttribute("success", "✔ Phiếu đã được kiểm và tồn kho đã cập nhật.");
        return "redirect:/ware-staff/stock-inward/check-list";
    }

}
