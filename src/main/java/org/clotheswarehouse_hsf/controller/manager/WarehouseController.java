package org.clotheswarehouse_hsf.controller.manager;

import org.clotheswarehouse_hsf.model.Inventory;
import org.clotheswarehouse_hsf.model.Warehouse;
import org.clotheswarehouse_hsf.repository.InventoryRepository;
import org.clotheswarehouse_hsf.service.ProductService;
import org.clotheswarehouse_hsf.service.WarehouseService;
import org.clotheswarehouse_hsf.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/manager/warehouse")
public class WarehouseController {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping
    public String listWarehouses(@RequestParam(value = "search", required = false) String searchTerm,
                                 Model model) {
        List<Warehouse> warehouses;

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            warehouses = warehouseService.findAll().stream()
                    .filter(w -> w.getWarehouseName().toLowerCase().contains(searchTerm.toLowerCase())
                            || w.getAddress().toLowerCase().contains(searchTerm.toLowerCase()))
                    .toList();
        } else {
            warehouses = warehouseService.findAll();
        }

        model.addAttribute("warehouses", warehouses);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("newWarehouse", new Warehouse());
        return "manager/warehouse/warehouse";
    }

    @PostMapping("/create")
    public String createWarehouse(@ModelAttribute("newWarehouse") Warehouse warehouse,
                                  RedirectAttributes redirectAttributes) {
        warehouse.setCreatedAt(LocalDateTime.now());
        warehouseService.save(warehouse);
        redirectAttributes.addFlashAttribute("message", "Thêm kho thành công!");
        return "redirect:/manager/warehouse";
    }

    @PostMapping("/update")
    public String updateWarehouse(@ModelAttribute("editWarehouse") Warehouse warehouse,
                                  RedirectAttributes redirectAttributes) {
        warehouseService.update(warehouse);
        redirectAttributes.addFlashAttribute("message", "Cập nhật kho thành công!");
        return "redirect:/manager/warehouse";
    }

    @GetMapping("/{id}/products")
    public String viewProductsInWarehouse(
            @PathVariable("id") Integer id,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "stock", required = false) String stock,
            Model model) {

        Optional<Warehouse> optionalWarehouse = warehouseService.findById(id);
        if (optionalWarehouse.isEmpty()) {
            return "redirect:/manager/warehouse";
        }
        Warehouse warehouse = optionalWarehouse.get();

        if (name != null && (name.trim().isEmpty() || name.equalsIgnoreCase("null"))) {
            name = null;
        }
        if (stock != null && (stock.trim().isEmpty() || stock.equalsIgnoreCase("null"))) {
            stock = null;
        }

        int total = inventoryRepository.countFiltered(name, id, stock);
        int offset = (page - 1) * size;
        int totalPages = (int) Math.ceil((double) total / size);

        List<Inventory> inventories = inventoryRepository.filterWithPaging(name, id, stock, offset, size);

        List<Integer> productIdsInWarehouse = inventories.stream()
                .map(inv -> inv.getProduct().getProductId())
                .toList();

        List<Product> allProducts = productService.findAll().stream()
                .filter(p -> !productIdsInWarehouse.contains(p.getProductId()))
                .toList();

        model.addAttribute("allProducts", allProducts);
        model.addAttribute("warehouse", warehouse);
        model.addAttribute("inventories", inventories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("name", name);
        model.addAttribute("stock", stock);

        return "manager/warehouse/ProductWarehouse";
    }

    @PostMapping("/product/add")
    public String addProductToWarehouse(@RequestParam("warehouseId") Integer warehouseId,
                                        @RequestParam("productId") Integer productId,
                                        RedirectAttributes redirectAttributes) {
        try {
            warehouseService.addProductToWarehouse(warehouseId, productId, 0);
            redirectAttributes.addFlashAttribute("message", "Đã thêm sản phẩm vào kho thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/manager/warehouse/" + warehouseId + "/products";
    }


    @PostMapping("/product/remove")
    public String removeProductFromWarehouse(@RequestParam("warehouseId") Integer warehouseId,
                                             @RequestParam("productId") Integer productId,
                                             RedirectAttributes redirectAttributes) {
        warehouseService.removeProductFromWarehouse(warehouseId, productId);
        redirectAttributes.addFlashAttribute("message", "Đã xóa sản phẩm khỏi kho!");
        return "redirect:/manager/warehouse/" + warehouseId + "/products";
    }

}
