package org.clotheswarehouse_hsf.controller.purchaseStaff;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.clotheswarehouse_hsf.model.*;
import org.clotheswarehouse_hsf.service.*;
import org.clotheswarehouse_hsf.utils.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.clotheswarehouse_hsf.model.enums.StockInwardStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/purchaseStaff/stockInward")
@RequiredArgsConstructor
public class StockInwardController {

    private final StockInwardService stockInwardService;
    private final SupplierService supplierService;
    private final WarehouseService warehouseService;
    private final ProductService productService;
    private final PurchaseOrderService purchaseOrderService;

    @GetMapping
    public String listStockInwards(Model model) {
        List<StockInward> stockInwards = stockInwardService.findAll();
        model.addAttribute("stockInwards", stockInwards);
        return "purchaseStaff/stockInward/stockInwardList";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession session) {
        StockInward stockInward = new StockInward();
        stockInward.setInwardCode(generateInwardCode());

        List<Supplier> suppliers = supplierService.findAll();

        Map<Integer, Supplier> supplierMap = suppliers.stream()
                .collect(Collectors.toMap(Supplier::getSupplierId, Function.identity()));

        List<Integer> usedPurchaseOrderIds = stockInwardService.findAll().stream()
                .filter(si -> si.getStatus() != StockInwardStatus.CANCELLED)
                .map(si -> si.getPurchaseOrder().getId())
                .toList();

        List<PurchaseOrder> purchaseOrders = purchaseOrderService.findAll().stream()
                .filter(po -> po.getStatus() == PurchaseOrder.OrderStatus.approved)
                .filter(po -> !usedPurchaseOrderIds.contains(po.getId()))
                .toList();

        Map<Integer, List<PurchaseOrderDetail>> orderDetailMap = purchaseOrders.stream()
                .collect(Collectors.toMap(
                        PurchaseOrder::getId,
                        PurchaseOrder::getOrderDetails
                ));

        model.addAttribute("stockInward", stockInward);
        model.addAttribute("warehouses", warehouseService.findAll());
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("supplierMap", supplierMap);
        model.addAttribute("orderDetailMap", orderDetailMap);
        model.addAttribute("purchaseOrders", purchaseOrders);
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);

        return "purchaseStaff/stockInward/createStockInward";
    }


    @PostMapping("/create")
    public String createStockInward(@ModelAttribute StockInward stockInward,
                                    @RequestParam("productIds") List<Integer> productIds,
                                    @RequestParam("quantities") List<Integer> quantities,
                                    @RequestParam("negotiatedPrices") List<BigDecimal> negotiatedPrices,
                                    @RequestParam("purchasePrices") List<BigDecimal> purchasePrices,
                                    RedirectAttributes redirectAttributes,
                                    HttpSession session) {

        if (productIds.size() != quantities.size() ||
                productIds.size() != negotiatedPrices.size() ||
                productIds.size() != purchasePrices.size()) {
            throw new IllegalArgumentException("Dữ liệu đầu vào không hợp lệ");
        }

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Phiên đăng nhập hết hạn. Vui lòng đăng nhập lại.");
            return "redirect:/login";
        }
        stockInward.setUser(currentUser);

        Optional<PurchaseOrder> optionalPurchaseOrder = purchaseOrderService.findById(stockInward.getPurchaseOrder().getId());
        if (!optionalPurchaseOrder.isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Đơn mua hàng không hợp lệ");
            return "redirect:/purchaseStaff/stockInward/create";
        }
        PurchaseOrder purchaseOrder = optionalPurchaseOrder.get();
        stockInward.setPurchaseOrder(purchaseOrder);

        Integer supplierId = purchaseOrder.getSupplierId();
        if (supplierId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Nhà cung cấp không hợp lệ");
            return "redirect:/purchaseStaff/stockInward/create";
        }

        Supplier supplier = supplierService.findById(supplierId);
        if (supplier == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Nhà cung cấp không tồn tại");
            return "redirect:/purchaseStaff/stockInward/create";
        }
        stockInward.setSupplier(supplier);

        List<StockInwardDetail> details = new ArrayList<>();
        for (int i = 0; i < productIds.size(); i++) {
            StockInwardDetail detail = new StockInwardDetail();
            detail.setProductId(productIds.get(i));
            detail.setQuantityReceived(quantities.get(i));
            detail.setUnitPriceNegotiated(negotiatedPrices.get(i));
            detail.setUnitPurchasePrice(purchasePrices.get(i));
            detail.setStockInward(stockInward);
            details.add(detail);
        }
        stockInward.setDetails(details);

        stockInward.setCreatedAt(LocalDateTime.now());
        stockInward.setStatus(StockInwardStatus.DRAFT);

        stockInwardService.save(stockInward);

        return "redirect:/purchaseStaff/stockInward";
    }

    private String generateInwardCode() {
        return "INW" + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
