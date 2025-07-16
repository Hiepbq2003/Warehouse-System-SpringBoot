package org.clotheswarehouse_hsf.controller.warehouseStaff;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;

import java.util.Map;

import org.clotheswarehouse_hsf.model.SalesOrder;
import org.clotheswarehouse_hsf.model.SalesOrderDetail;
import org.clotheswarehouse_hsf.service.SalesOrderService;

@Controller
@RequestMapping("/wareStaff/salesOrder")
public class SaleWareStaffController {

    @Autowired
    private SalesOrderService salesOrderService;

    // Hiển thị danh sách đơn hàng
    @GetMapping("/list")
    public String listOrders(Model model,
                             @RequestParam(defaultValue = "1") int page) {
        Page<SalesOrder> ordersPage = salesOrderService.findByStatus("awaiting_shipment", page);
        model.addAttribute("orders", ordersPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ordersPage.getTotalPages());
        return "wareStaff/saleOrder/saleOrderList";
    }

    // Hiển thị form sửa đơn
    @GetMapping("/edit/{id}")
    public String editOrder(@PathVariable Integer id, Model model, RedirectAttributes ra) {
        SalesOrder order = salesOrderService.findById(id);
        if (order == null) {
            ra.addFlashAttribute("error", "Không tìm thấy đơn hàng.");
            return "redirect:/wareStaff/salesOrder/list";
        }
        if (order.getStatus() != SalesOrder.OrderStatus.awaiting_shipment) {
            ra.addFlashAttribute("success", "Đơn hàng đã được giao!.");
            return "redirect:/wareStaff/salesOrder/list";
        }
        model.addAttribute("order", order);

        // Truyền thêm map hiển thị tên trạng thái (nếu cần)
        Map<SalesOrder.OrderStatus, String> statusDisplayNames = Map.of(
                SalesOrder.OrderStatus.pending_stock_check, "Chờ kiểm kho",
                SalesOrder.OrderStatus.awaiting_shipment, "Chờ giao hàng",
                SalesOrder.OrderStatus.shipped, "Đã giao",
                SalesOrder.OrderStatus.completed, "Hoàn thành",
                SalesOrder.OrderStatus.cancelled, "Đã hủy"
        );
        model.addAttribute("statusDisplayNames", statusDisplayNames);

        return "wareStaff/saleOrder/editSaleOrder";
    }


    @PostMapping("/edit")
    public String updateOrder(@ModelAttribute SalesOrder orderForm,
                              @RequestParam String action,
                              RedirectAttributes ra) {
        SalesOrder order = salesOrderService.findById(orderForm.getSalesOrderId());
        if (order == null || order.getStatus() != SalesOrder.OrderStatus.awaiting_shipment) {
            ra.addFlashAttribute("error", "Đơn hàng không tồn tại hoặc không đúng trạng thái.");
            return "redirect:/wareStaff/salesOrder/list";
        }
        if (orderForm.getOrderDetails() != null) {
            for (SalesOrderDetail detailForm : orderForm.getOrderDetails()) {
                SalesOrderDetail detail = order.getOrderDetails().stream()
                        .filter(d -> d.getOrderDetailId().equals(detailForm.getOrderDetailId()))
                        .findFirst().orElse(null);
                if (detail != null) {
                    detail.setQuantityOrdered(detailForm.getQuantityOrdered());
                }
            }
        }

        if ("markDelivered".equals(action)) {
            order.setStatus(SalesOrder.OrderStatus.shipped);
            salesOrderService.save(order);
            salesOrderService.markAsShippedAndSendEmail(order.getSalesOrderId());
        }


        salesOrderService.save(order);
        ra.addFlashAttribute("success", "Cập nhật đơn hàng thành công!");

        ra.addFlashAttribute("success", "Cập nhật đơn hàng thành công!");
        return "redirect:/wareStaff/salesOrder/edit/" + order.getSalesOrderId();

    }

    @GetMapping("/salesOrder/confirm")
    @ResponseBody
    public String confirmPaymentFromEmail(@RequestParam("id") Integer orderId) {
        try {
            salesOrderService.markAsCompleted(orderId);
            return "<h3>✅ Đã xác nhận thanh toán thành công!</h3>";
        } catch (Exception e) {
            return "<h3 style='color:red;'>❌ Lỗi: " + e.getMessage() + "</h3>";
        }
    }

}
