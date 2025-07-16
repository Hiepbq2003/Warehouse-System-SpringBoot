package org.clotheswarehouse_hsf.controller.manager;

import org.apache.poi.ss.usermodel.Row;
import org.clotheswarehouse_hsf.model.Inventory;
import org.clotheswarehouse_hsf.service.InventoryService;
import org.clotheswarehouse_hsf.service.ProductService;
import org.clotheswarehouse_hsf.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

@Controller
@RequestMapping("/manager/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping
    public String listInventories(
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "warehouseId", required = false) Integer warehouseId,
            @RequestParam(value = "stockStatus", required = false) String stockStatus,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model
    ) {
        int totalItems = inventoryService.countFilteredInventories(productName, warehouseId, stockStatus);
        int totalPages = (int) Math.ceil((double) totalItems / size);
        if (page < 1) page = 1;
        if (page > totalPages && totalPages > 0) page = totalPages;

        List<Inventory> inventories = inventoryService.findFilteredInventories(productName, warehouseId, stockStatus, page, size);

        model.addAttribute("inventories", inventories);
        model.addAttribute("products", productService.findAll());
        model.addAttribute("warehouses", warehouseService.findAll());

        // Trả lại dữ liệu lọc để giữ nguyên sau khi submit
        model.addAttribute("productName", productName);
        model.addAttribute("selectedWarehouseId", warehouseId);
        model.addAttribute("stockStatus", stockStatus);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);

        return "manager/warehouse/inventory";
    }

    @GetMapping("/find/{id}")
    @ResponseBody
    public Inventory findById(@PathVariable("id") Integer id) {
        return inventoryService.findById(id).orElse(null);
    }

    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ton_kho.xlsx";
        response.setHeader(headerKey, headerValue);

        List<Inventory> inventories = inventoryService.findAll(); // hoặc lọc nếu cần

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Tồn Kho");

        // Header
        Row headerRow = sheet.createRow(0);
        String[] headers = {"STT", "Kho", "Mã Sản Phẩm", "Tên Sản Phẩm", "Tồn kho", "Giá mua", "Giá bán", "Tổng giá tồn kho", "Trạng thái"};
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        // Dữ liệu
        int rowNum = 1;
        for (Inventory inv : inventories) {
            Row row = sheet.createRow(rowNum++);
            int index = rowNum - 1;

            // Tính trạng thái tồn kho
            String status;
            if (inv.getQuantityOnHand() == 0) {
                status = "Hết hàng";
            } else if (inv.getQuantityOnHand() <= (inv.getProduct().getLowStockThreshold() != null ? inv.getProduct().getLowStockThreshold() : 0)) {
                status = "Sắp hết";
            } else {
                status = "Bình thường";
            }

            // Ghi dữ liệu từng dòng
            row.createCell(0).setCellValue(index);
            row.createCell(1).setCellValue(inv.getWarehouse().getWarehouseName());
            row.createCell(2).setCellValue(inv.getProduct().getProductCode());
            row.createCell(3).setCellValue(inv.getProduct().getProductName());
            row.createCell(4).setCellValue(inv.getQuantityOnHand());
            row.createCell(5).setCellValue(inv.getProduct().getPurchasePrice());
            row.createCell(6).setCellValue(inv.getProduct().getSalePrice());
            row.createCell(7).setCellValue(inv.getQuantityOnHand() * inv.getProduct().getPurchasePrice());
            row.createCell(8).setCellValue(status);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
