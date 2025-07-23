package org.clotheswarehouse_hsf.controller.manager;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.clotheswarehouse_hsf.model.Category;
import org.clotheswarehouse_hsf.model.Product;
import org.clotheswarehouse_hsf.model.Supplier;
import org.clotheswarehouse_hsf.service.CategoryService;
import org.clotheswarehouse_hsf.service.ProductService;
import org.clotheswarehouse_hsf.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/manager/manage-product")
public class ManageProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public String listProducts(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "active", required = false) String activeStr,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model) {

        Boolean active = null;
        if ("true".equalsIgnoreCase(activeStr)) active = true;
        else if ("false".equalsIgnoreCase(activeStr)) active = false;

        int totalItems = productService.countSearchWithFilter(keyword, active);
        int totalPages = (int) Math.ceil((double) totalItems / size);
        if (page < 1) page = 1;
        if (page > totalPages && totalPages > 0) page = totalPages;

        List<Product> products = productService.searchWithFilter(keyword, active, page, size);

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("searchTerm", keyword);
        model.addAttribute("activeFilter", activeStr);

        model.addAttribute("newProduct", new Product());
        model.addAttribute("categories", categoryService.findAll());

        return "manager/product/product";
    }

    @GetMapping("/create")
    public String showAddProductForm(Model model) {
        model.addAttribute("newProduct", new Product());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("suppliers", supplierService.getAll());
        return "manager/product/addProduct";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute("newProduct") Product product,
                                HttpSession session,
                                RedirectAttributes ra,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "") String keyword,
                                @RequestParam(defaultValue = "") String active) {


        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        productService.save(product);

        ra.addFlashAttribute("message", "Thêm sản phẩm thành công!");

        return "redirect:/manager/manage-product?page=%d&keyword=%s&active=%s"
                .formatted(page, keyword, active);
    }


    // ✅ Hiển thị form sửa
    @GetMapping("/get/{id}")
    @ResponseBody
    public Product getProductById(@PathVariable int id) {
        return productService.getById(id);
    }

    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute Product product,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(required = false) String keyword,
                                @RequestParam(required = false) Boolean active,
                                RedirectAttributes ra) {

        productService.save(product);
        ra.addFlashAttribute("message", "Cập nhật sản phẩm thành công!");

        return "redirect:/manager/manage-product?page=" + page
                + (keyword != null ? "&keyword=" + keyword : "")
                + (active != null ? "&active=" + active : "");
    }


    // ✅ Xoá sản phẩm
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id, RedirectAttributes ra) {
        productService.deleteById(id);
        ra.addFlashAttribute("message", "Xoá sản phẩm thành công!");
        return "redirect:/manager/manage-product";
    }

    // ✅ Xuất Excel
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<Product> products = productService.findAll();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Products");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Mã sản phẩm");         // productCode
        header.createCell(1).setCellValue("Tên sản phẩm");        // productName
        header.createCell(2).setCellValue("Mô tả");               // description
        header.createCell(3).setCellValue("Giá mua");             // purchasePrice
        header.createCell(4).setCellValue("Giá bán");             // salePrice
        header.createCell(5).setCellValue("Trạng thái");          // isActive
        header.createCell(6).setCellValue("Nhà cung cấp");        // supplier name
        header.createCell(7).setCellValue("Danh mục");            // category name
        header.createCell(8).setCellValue("Đơn vị tính");         // unit
        header.createCell(9).setCellValue("Tồn kho tối thiểu");   // lowStockThreshold

        int rowNum = 1;
        for (Product product : products) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(product.getProductCode());
            row.createCell(1).setCellValue(product.getProductName());
            row.createCell(2).setCellValue(product.getDescription());
            row.createCell(3).setCellValue(product.getPurchasePrice());
            row.createCell(4).setCellValue(product.getSalePrice());
            row.createCell(5).setCellValue(product.getIsActive() ? "Có" : "Không");
            row.createCell(6).setCellValue(product.getSupplier() != null ? product.getSupplier().getSupplierName() : ""
            );
            row.createCell(7).setCellValue(
                    product.getCategory() != null ? product.getCategory().getName() : ""
            );
            row.createCell(8).setCellValue(product.getUnit());
            row.createCell(9).setCellValue(
                    product.getLowStockThreshold() != null ? product.getLowStockThreshold() : 0
            );
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=products.xlsx");

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }


    // ✅ Nhập Excel
    @PostMapping("/import")
    public String importExcel(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("toastMessage", "Vui lòng chọn file Excel!");
            return "redirect:/manager/manage-product";
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            int importCount = 0;
            int duplicateCount = 0;
            int errorCount = 0;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    String productCode = row.getCell(0).getStringCellValue().trim();
                    String productName = row.getCell(1).getStringCellValue().trim();
                    String description = row.getCell(2).getStringCellValue().trim();
                    float purchasePrice = (float) row.getCell(3).getNumericCellValue();
                    float salePrice = (float) row.getCell(4).getNumericCellValue();
                    String status = row.getCell(5).getStringCellValue().trim();
                    String supplierName = row.getCell(6).getStringCellValue().trim();
                    String categoryName = row.getCell(7).getStringCellValue().trim();
                    int lowStockThreshold = (int) row.getCell(9).getNumericCellValue();

                    Category category = categoryService.findByName(categoryName);
                    Supplier supplier = supplierService.findBySupplierName(supplierName);
                    if (category == null || supplier == null) {
                        errorCount++;
                        continue;
                    }

                    if (productService.existsByProductCode(productCode)) {
                        duplicateCount++;
                        continue;
                    }

                    Product product = Product.builder()
                            .productCode(productCode)
                            .productName(productName)
                            .description(description)
                            .purchasePrice(purchasePrice)
                            .salePrice(salePrice)
                            .isActive("Có".equalsIgnoreCase(status))
                            .supplier(supplier)
                            .category(category)
                            .lowStockThreshold(lowStockThreshold)
                            .build();

                    productService.save(product);
                    importCount++;
                } catch (Exception ex) {
                    System.out.println("Lỗi tại dòng " + (i + 1) + ": " + ex.getMessage());
                    errorCount++;
                }
            }

            redirectAttributes.addFlashAttribute("message",
                    "Nhập Excel thành công: " + importCount + " sản phẩm mới. " +
                            (duplicateCount > 0 ? duplicateCount + " bị trùng mã. " : "") +
                            (errorCount > 0 ? errorCount + " dòng lỗi." : "")
            );
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi nhập file Excel!");
        }

        return "redirect:/manager/manage-product";
    }


}
