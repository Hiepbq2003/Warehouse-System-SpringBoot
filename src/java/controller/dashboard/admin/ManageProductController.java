package controller.dashboard.admin;

import dao.ProductDAO;
import dao.SupplierDAO;
import dao.CategoryDAO;
import model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.Supplier;
import model.Category;
import java.util.ArrayList;

@WebServlet(name = "ManageProductController", urlPatterns = {"/admin/manage-product"})
public class ManageProductController extends HttpServlet {

    private ProductDAO productDAO;
    private SupplierDAO supplierDAO;
    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        productDAO = new ProductDAO();
        supplierDAO = new SupplierDAO();
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list"; // Default action
        }

        switch (action) {
            case "list":
                listProducts(request, response);
                break;
            case "create":
                showCreateForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteProduct(request, response);
                break;
            case "init-sample-data":
                initializeSampleData(request, response);
                break;
            default:
                listProducts(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/admin/manage-product?action=list");
            return;
        }

        switch (action) {
            case "create":
                createProduct(request, response);
                break;
            case "edit":
                updateProduct(request, response);
                break;
            default:
                listProducts(request, response);
                break;
        }
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Pagination and filtering parameters
        String searchTerm = request.getParameter("search");
        String supplierIdStr = request.getParameter("supplierId");
        String categoryIdStr = request.getParameter("categoryId");
        String isActiveStr = request.getParameter("isActive");
        String minPurchasePriceStr = request.getParameter("minPurchasePrice");
        String minSalePriceStr = request.getParameter("minSalePrice");
        String pageStr = request.getParameter("page");
        int page = (pageStr == null || pageStr.isEmpty()) ? 1 : Integer.parseInt(pageStr);
        int pageSize = 10; // Or get from a config

        Integer supplierId = null;
        if (supplierIdStr != null && !supplierIdStr.isEmpty()) {
            try {
                supplierId = Integer.parseInt(supplierIdStr);
            } catch (NumberFormatException e) {
                // Handle error or log
            }
        }

        Integer categoryId = null;
        if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
            try {
                categoryId = Integer.parseInt(categoryIdStr);
            } catch (NumberFormatException e) {
                // Handle error or log
            }
        }

        Boolean isActive = null;
        if (isActiveStr != null && !isActiveStr.isEmpty()) {
            isActive = Boolean.parseBoolean(isActiveStr);
        }

        Float minPurchasePrice = null;
        if (minPurchasePriceStr != null && !minPurchasePriceStr.isEmpty()) {
            try {
                minPurchasePrice = Float.parseFloat(minPurchasePriceStr);
            } catch (NumberFormatException e) {
                // Handle error or log
            }
        }

        Float minSalePrice = null;
        if (minSalePriceStr != null && !minSalePriceStr.isEmpty()) {
            try {
                minSalePrice = Float.parseFloat(minSalePriceStr);
            } catch (NumberFormatException e) {
                // Handle error or log
            }
        }

        List<Product> products = productDAO.findProducts(searchTerm, supplierId, categoryId, isActive, minPurchasePrice, minSalePrice, page, pageSize);
        int totalProducts = productDAO.getTotalFilteredProducts(searchTerm, supplierId, categoryId, isActive, minPurchasePrice, minSalePrice);
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        // Get all suppliers and categories for dropdown filters
        List<Supplier> suppliers = supplierDAO.findAll();
        List<Category> categories = categoryDAO.findAll();
        
        // Create a map to store primary category for each product
        java.util.Map<Integer, Category> productCategoryMap = new java.util.HashMap<>();
        for (Product product : products) {
            Category primaryCategory = productDAO.getPrimaryCategoryForProduct(product.getProductId());
            if (primaryCategory != null) {
                productCategoryMap.put(product.getProductId(), primaryCategory);
            }
        }
        
        request.setAttribute("supplierDAO", supplierDAO);
        request.setAttribute("categoryDAO", categoryDAO);
        request.setAttribute("suppliers", suppliers);
        request.setAttribute("categories", categories);
        request.setAttribute("products", products);
        request.setAttribute("productCategoryMap", productCategoryMap);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("searchTerm", searchTerm);
        request.setAttribute("supplierId", supplierIdStr); // Keep as string for form repopulation
        request.setAttribute("categoryId", categoryIdStr); // Keep as string for form repopulation
        request.setAttribute("isActive", isActiveStr); // Keep as string for form repopulation
        request.setAttribute("minPurchasePrice", minPurchasePriceStr);
        request.setAttribute("minSalePrice", minSalePriceStr);

        request.getRequestDispatcher("/view/dashboard/admin/product/product.jsp").forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Add list of suppliers and categories for the dropdowns
        List<Supplier> suppliers = supplierDAO.findAll();
        List<Category> categories = categoryDAO.findAll();
        request.setAttribute("suppliers", suppliers);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/view/dashboard/admin/product/addProduct.jsp").forward(request, response);
    }

    private void createProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String productCode = request.getParameter("productCode");
            String productName = request.getParameter("productName");
            String description = request.getParameter("description");
            String unit = request.getParameter("unit");
            String purchasePriceStr = request.getParameter("purchasePrice");
            String salePriceStr = request.getParameter("salePrice");
            String lowStockThresholdStr = request.getParameter("lowStockThreshold");
            Integer supplierId = request.getParameter("supplierId") != null && !request.getParameter("supplierId").isEmpty() ? Integer.parseInt(request.getParameter("supplierId")) : null;
            Boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

            // Validation
            StringBuilder errorMessages = new StringBuilder();
            
            if (productCode == null || productCode.trim().isEmpty()) {
                errorMessages.append("Mã sản phẩm không được để trống. ");
            } else if (productCode.trim().length() > 10) {
                errorMessages.append("Mã sản phẩm không được quá 10 ký tự. ");
            }
            
            if (productName == null || productName.trim().isEmpty()) {
                errorMessages.append("Tên sản phẩm không được để trống. ");
            } else if (productName.trim().length() > 10) {
                errorMessages.append("Tên sản phẩm không được quá 10 ký tự. ");
            }
            
            if (description == null || description.trim().isEmpty()) {
                errorMessages.append("Mô tả không được để trống. ");
            } else if (description.trim().length() > 100) {
                errorMessages.append("Mô tả không được quá 100 ký tự. ");
            }
            
            if (unit == null || unit.trim().isEmpty()) {
                errorMessages.append("Đơn vị tính không được để trống. ");
            }
            
            if (supplierId == null) {
                errorMessages.append("Nhà cung cấp không được để trống. ");
            }
            
            String categoryIdStr = request.getParameter("categoryId");
            if (categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
                errorMessages.append("Danh mục không được để trống. ");
            }
            
            Float purchasePrice = null;
            if (purchasePriceStr == null || purchasePriceStr.trim().isEmpty()) {
                errorMessages.append("Giá mua không được để trống. ");
            } else {
                purchasePrice = Float.parseFloat(purchasePriceStr);
                if (purchasePrice < 0) {
                    errorMessages.append("Giá mua không được âm. ");
                }
            }
            
            Float salePrice = null;
            if (salePriceStr == null || salePriceStr.trim().isEmpty()) {
                errorMessages.append("Giá bán không được để trống. ");
            } else {
                salePrice = Float.parseFloat(salePriceStr);
                if (salePrice < 0) {
                    errorMessages.append("Giá bán không được âm. ");
                }
            }
            
            Integer lowStockThreshold = null;
            if (lowStockThresholdStr == null || lowStockThresholdStr.trim().isEmpty()) {
                errorMessages.append("Ngưỡng tồn kho thấp không được để trống. ");
            } else {
                lowStockThreshold = Integer.parseInt(lowStockThresholdStr);
                if (lowStockThreshold < 10) {
                    errorMessages.append("Ngưỡng tồn kho thấp phải ít nhất là 10. ");
                }
            }
            
            if (errorMessages.length() > 0) {
                request.getSession().setAttribute("toastMessage", errorMessages.toString());
                request.getSession().setAttribute("toastType", "error");
                response.sendRedirect(request.getContextPath() + "/admin/manage-product?action=create");
                return;
            }

            Product product = Product.builder()
                    .productCode(productCode.trim())
                    .productName(productName.trim())
                    .description(description != null ? description.trim() : "")
                    .unit(unit)
                    .purchasePrice(purchasePrice)
                    .salePrice(salePrice)
                    .supplierId(supplierId)
                    .lowStockThreshold(lowStockThreshold)
                    .isActive(isActive)
                    .build();

            int generatedId = productDAO.insert(product);
            if (generatedId > 0) {
                // Handle category assignment if provided
//                String categoryIdStr = request.getParameter("categoryId");
                if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
                    try {
                        Integer categoryId = Integer.parseInt(categoryIdStr);
                        List<Integer> categories = new ArrayList<>();
                        categories.add(categoryId);
                        productDAO.updateProductCategories(generatedId, categories);
                    } catch (NumberFormatException e) {
                        // Log error but don't fail the product creation
                    }
                }
                
                request.getSession().setAttribute("toastMessage", "Product created successfully!");
                request.getSession().setAttribute("toastType", "success");
            } else {
                request.getSession().setAttribute("toastMessage", "Error creating product.");
                request.getSession().setAttribute("toastType", "error");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("toastMessage", "Invalid number format for price or threshold.");
            request.getSession().setAttribute("toastType", "error");
        } catch (Exception e) {
            request.getSession().setAttribute("toastMessage", "An unexpected error occurred: " + e.getMessage());
            request.getSession().setAttribute("toastType", "error");
        }
        response.sendRedirect(request.getContextPath() + "/admin/manage-product?action=list");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("id"));
            Product product = productDAO.findById(productId);
            if (product != null) {
                request.setAttribute("product", product);
                // Add list of suppliers and categories for the dropdowns
                List<Supplier> suppliers = supplierDAO.findAll();
                List<Category> categories = categoryDAO.findAll();
                List<Category> productCategories = productDAO.getCategoriesForProduct(productId);
                request.setAttribute("suppliers", suppliers);
                request.setAttribute("categories", categories);
                request.setAttribute("productCategories", productCategories);
                request.getRequestDispatcher("/view/dashboard/admin/product/edit-product.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("toastMessage", "Product not found.");
                request.getSession().setAttribute("toastType", "error");
                response.sendRedirect(request.getContextPath() + "/admin/manage-product?action=list");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("toastMessage", "Invalid product ID.");
            request.getSession().setAttribute("toastType", "error");
            response.sendRedirect(request.getContextPath() + "/admin/manage-product?action=list");
        }
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer productId = Integer.parseInt(request.getParameter("productId"));
            String productCode = request.getParameter("productCode");
            String productName = request.getParameter("productName");
            String description = request.getParameter("description");
            String unit = request.getParameter("unit");
            String purchasePriceStr = request.getParameter("purchasePrice");
            String salePriceStr = request.getParameter("salePrice");
            String lowStockThresholdStr = request.getParameter("lowStockThreshold");
            Integer supplierId = request.getParameter("supplierId") != null && !request.getParameter("supplierId").isEmpty() ? Integer.parseInt(request.getParameter("supplierId")) : null;
            Boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

            // Validation
            StringBuilder errorMessages = new StringBuilder();
            
            if (productCode == null || productCode.trim().isEmpty()) {
                errorMessages.append("Mã sản phẩm không được để trống. ");
            } else if (productCode.trim().length() > 10) {
                errorMessages.append("Mã sản phẩm không được quá 10 ký tự. ");
            }
            
            if (productName == null || productName.trim().isEmpty()) {
                errorMessages.append("Tên sản phẩm không được để trống. ");
            } else if (productName.trim().length() > 10) {
                errorMessages.append("Tên sản phẩm không được quá 10 ký tự. ");
            }
            
            if (description == null || description.trim().isEmpty()) {
                errorMessages.append("Mô tả không được để trống. ");
            } else if (description.trim().length() > 100) {
                errorMessages.append("Mô tả không được quá 100 ký tự. ");
            }
            
            if (unit == null || unit.trim().isEmpty()) {
                errorMessages.append("Đơn vị tính không được để trống. ");
            }
            
            if (supplierId == null) {
                errorMessages.append("Nhà cung cấp không được để trống. ");
            }
            
            String categoryIdStr = request.getParameter("categoryId");
            if (categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
                errorMessages.append("Danh mục không được để trống. ");
            }
            
            Float purchasePrice = null;
            if (purchasePriceStr == null || purchasePriceStr.trim().isEmpty()) {
                errorMessages.append("Giá mua không được để trống. ");
            } else {
                purchasePrice = Float.parseFloat(purchasePriceStr);
                if (purchasePrice < 0) {
                    errorMessages.append("Giá mua không được âm. ");
                }
            }
            
            Float salePrice = null;
            if (salePriceStr == null || salePriceStr.trim().isEmpty()) {
                errorMessages.append("Giá bán không được để trống. ");
            } else {
                salePrice = Float.parseFloat(salePriceStr);
                if (salePrice < 0) {
                    errorMessages.append("Giá bán không được âm. ");
                }
            }
            
            Integer lowStockThreshold = null;
            if (lowStockThresholdStr == null || lowStockThresholdStr.trim().isEmpty()) {
                errorMessages.append("Ngưỡng tồn kho thấp không được để trống. ");
            } else {
                lowStockThreshold = Integer.parseInt(lowStockThresholdStr);
                if (lowStockThreshold < 10) {
                    errorMessages.append("Ngưỡng tồn kho thấp phải ít nhất là 10. ");
                }
            }
            
            if (errorMessages.length() > 0) {
                request.getSession().setAttribute("toastMessage", errorMessages.toString());
                request.getSession().setAttribute("toastType", "error");
                response.sendRedirect(request.getContextPath() + "/admin/manage-product?action=edit&id=" + productId);
                return;
            }

            Product product = Product.builder()
                    .productId(productId)
                    .productCode(productCode.trim())
                    .productName(productName.trim())
                    .description(description != null ? description.trim() : "")
                    .unit(unit)
                    .purchasePrice(purchasePrice)
                    .salePrice(salePrice)
                    .supplierId(supplierId)
                    .lowStockThreshold(lowStockThreshold)
                    .isActive(isActive)
                    .build();

            boolean success = productDAO.update(product);
            if (success) {
                // Handle category updates
//                String categoryIdStr = request.getParameter("categoryId");
                List<Integer> categories = new ArrayList<>();
                if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
                    try {
                        Integer categoryId = Integer.parseInt(categoryIdStr);
                        categories.add(categoryId);
                    } catch (NumberFormatException e) {
                        // Log error but don't fail the product update
                    }
                }
                // Update categories (empty list will clear all categories)
                productDAO.updateProductCategories(productId, categories);
                
                request.getSession().setAttribute("toastMessage", "Product updated successfully!");
                request.getSession().setAttribute("toastType", "success");
            } else {
                request.getSession().setAttribute("toastMessage", "Error updating product.");
                request.getSession().setAttribute("toastType", "error");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("toastMessage", "Invalid number format for price or threshold.");
            request.getSession().setAttribute("toastType", "error");
        } catch (Exception e) {
            request.getSession().setAttribute("toastMessage", "An unexpected error occurred: " + e.getMessage());
            request.getSession().setAttribute("toastType", "error");
        }
        response.sendRedirect(request.getContextPath() + "/admin/manage-product?action=list");
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("id"));
            boolean success = productDAO.delete(productId); // Assuming ProductDAO has a delete method by ID
            if (success) {
                request.getSession().setAttribute("toastMessage", "Product deleted successfully!");
                request.getSession().setAttribute("toastType", "success");
            } else {
                request.getSession().setAttribute("toastMessage", "Error deleting product.");
                request.getSession().setAttribute("toastType", "error");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("toastMessage", "Invalid product ID.");
            request.getSession().setAttribute("toastType", "error");
        }
        response.sendRedirect(request.getContextPath() + "/admin/manage-product?action=list");
    }

    private void initializeSampleData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            boolean success = productDAO.initializeSampleCategoryProductData();
            if (success) {
                request.getSession().setAttribute("toastMessage", "Sample category-product data initialized successfully!");
                request.getSession().setAttribute("toastType", "success");
            } else {
                request.getSession().setAttribute("toastMessage", "Error initializing sample data.");
                request.getSession().setAttribute("toastType", "error");
            }
        } catch (Exception e) {
            request.getSession().setAttribute("toastMessage", "An error occurred: " + e.getMessage());
            request.getSession().setAttribute("toastType", "error");
        }
        response.sendRedirect(request.getContextPath() + "/admin/manage-product?action=list");
    }
}
