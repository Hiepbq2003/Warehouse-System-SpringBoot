package org.clotheswarehouse_hsf.controller.admin;

import org.clotheswarehouse_hsf.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {
    private final ProductService productService;
    private final UserService userService;
    private final SalesOrderService salesOrderService;
    private final StockInwardService stockInwardService;
    private final InventoryService inventoryService;

    @Autowired
    public AdminDashboardController(
            ProductService productService,
            UserService userService,
            SalesOrderService salesOrderService,
            StockInwardService stockInwardService,
            InventoryService inventoryService
    ) {
        this.productService = productService;
        this.userService = userService;
        this.salesOrderService = salesOrderService;
        this.stockInwardService = stockInwardService;
        this.inventoryService = inventoryService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("totalUsers", userService.count());
        model.addAttribute("totalProducts", productService.count());
        model.addAttribute("totalSalesOrders", salesOrderService.count());
        model.addAttribute("totalStockInwards", stockInwardService.count());
        model.addAttribute("totalInventoryQuantity", inventoryService.getTotalQuantityOnHand());
        model.addAttribute("lowStockCount", inventoryService.countLowStock());
        model.addAttribute("outOfStockCount", inventoryService.countOutOfStock());

        List<String> orderChartLabels = new ArrayList<>();
        List<Integer> salesOrderData = new ArrayList<>();
        List<Integer> stockInwardData = new ArrayList<>();
        List<Integer> inventoryQuantityData = new ArrayList<>();

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

        for (int i = 29; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            orderChartLabels.add(date.format(formatter));

            salesOrderData.add((int)(Math.random() * 20));
            stockInwardData.add((int)(Math.random() * 15));
            inventoryQuantityData.add(100 + (int)(Math.random() * 20)); // fluctuating
        }

        model.addAttribute("orderChartLabels", orderChartLabels);
        model.addAttribute("salesOrderData", salesOrderData);
        model.addAttribute("stockInwardData", stockInwardData);
        model.addAttribute("inventoryChartLabels", orderChartLabels);
        model.addAttribute("inventoryQuantityData", inventoryQuantityData);
        return "admin/dashboard/dashboard";
    }
}
