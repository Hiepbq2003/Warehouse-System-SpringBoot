package org.clotheswarehouse_hsf.controller.manager;

import lombok.RequiredArgsConstructor;
import org.clotheswarehouse_hsf.model.SalesOrder;
import org.clotheswarehouse_hsf.service.SalesOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manager/salesOrder")
@RequiredArgsConstructor
public class ManagerSalesOrderController {

    private final SalesOrderService salesOrderService;

    @GetMapping("/list")
    public String viewSalesOrderList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String customer,
            Model model
    ) {
        int pageSize = 10;

        List<SalesOrder> orders = salesOrderService.filterOrders(customer, status, null, page);
        int totalPages = salesOrderService.getTotalPages(customer, status, null);

        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("statusFilter", status);
        model.addAttribute("customerFilter", customer);

        model.addAttribute("statusDisplayNames", getStatusDisplayNames());

        return "manager/saleOrder/manageSalesOrderList";
    }

    @PostMapping("/approve")
    public String approveOrder(
            @RequestParam("id") Integer id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String customer
    ) {
        salesOrderService.approveOrder(id);

        StringBuilder redirectUrl = new StringBuilder("redirect:/manager/salesOrder/list?page=" + page);

        if (status != null && !status.isEmpty()) {
            redirectUrl.append("&status=").append(status);
        }
        if (customer != null && !customer.isEmpty()) {
            redirectUrl.append("&customer=").append(customer);
        }

        return redirectUrl.toString();
    }

    @PostMapping("/cancel")
    public String cancelOrder(
            @RequestParam("id") Integer id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String customer
    ) {
        salesOrderService.updateStatus(id, SalesOrder.OrderStatus.cancelled);

        StringBuilder redirectUrl = new StringBuilder("redirect:/manager/salesOrder/list?page=" + page);

        if (status != null && !status.isEmpty()) {
            redirectUrl.append("&status=").append(status);
        }
        if (customer != null && !customer.isEmpty()) {
            redirectUrl.append("&customer=").append(customer);
        }

        return redirectUrl.toString();
    }


    @GetMapping("/view/{id}")
    public String viewOrder(@PathVariable Integer id, Model model) {
        SalesOrder order = salesOrderService.findById(id);
        model.addAttribute("order", order);
        model.addAttribute("statusDisplayNames", getStatusDisplayNames());

        double totalOrderValue = order.getOrderDetails().stream()
                .mapToDouble(detail -> detail.getQuantityOrdered() * detail.getUnitSalePrice())
                .sum();
        model.addAttribute("totalOrderValue", totalOrderValue);

        return "manager/saleOrder/viewSaleOrder";
    }

    private Map<String, String> getStatusDisplayNames() {
        Map<String, String> statusDisplayNames = new LinkedHashMap<>();
        statusDisplayNames.put("pending_stock_check", "Chờ kiểm tra kho");
        statusDisplayNames.put("awaiting_shipment", "Chờ giao hàng");
        statusDisplayNames.put("shipped", "Đã giao");
        statusDisplayNames.put("completed", "Hoàn thành");
        statusDisplayNames.put("cancelled", "Đã hủy");
        return statusDisplayNames;
    }
}
