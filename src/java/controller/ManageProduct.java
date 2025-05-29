package controller;

import context.DBContext;
import dao.ProductDAO;
import dao.SupplierDAO;
import model.Product;
import model.Supplier;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ManageProductController", urlPatterns = {"/manageProduct"})
public class ManageProduct extends HttpServlet {

    private DBContext dbContext;
    private Connection conn;
    private ProductDAO productDAO;
    private SupplierDAO supplierDAO;

    @Override
    public void init() throws ServletException {
        try {
            dbContext = new DBContext();
            conn = dbContext.getConnection();
            productDAO = new ProductDAO(conn);
            supplierDAO = new SupplierDAO(conn);
        } catch (Exception e) {
            throw new ServletException("Lỗi khởi tạo DAO: " + e.getMessage(), e);
        }
    }

    @Override
    public void destroy() {
        try {
            if (conn != null && !conn.isClosed()) conn.close();
            if (dbContext != null) dbContext.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            if ("list".equals(action)) {
                listProducts(req, resp);
            } else if ("create".equals(action)) {
                showCreateForm(req, resp);
            } else if ("edit".equals(action)) {
                showEditForm(req, resp);
            } else if ("delete".equals(action)) {
                deleteProduct(req, resp);
            } else {
                listProducts(req, resp);
            }
        } catch (Exception e) {
            setToast(req, "Lỗi xử lý yêu cầu: " + e.getMessage(), "error");
            resp.sendRedirect(req.getContextPath() + "/admin/manage-product?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/manage-product?action=list");
            return;
        }
        try {
            if ("create".equals(action)) {
                createProduct(req, resp);
            } else if ("edit".equals(action)) {
                updateProduct(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/admin/manage-product?action=list");
            }
        } catch (Exception e) {
            setToast(req, "Lỗi xử lý dữ liệu: " + e.getMessage(), "error");
            resp.sendRedirect(req.getContextPath() + "/admin/manage-product?action=list");
        }
    }

    private void listProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        String search = req.getParameter("search");
        Integer supplierId = parseIntOrNull(req.getParameter("supplierId"));
        Boolean isActive = parseBooleanOrNull(req.getParameter("isActive"));
        Float minPurchasePrice = parseFloatOrNull(req.getParameter("minPurchasePrice"));
        Float minSalePrice = parseFloatOrNull(req.getParameter("minSalePrice"));
        int page = parseIntOrDefault(req.getParameter("page"), 1);
        int pageSize = 10;

        List<Product> products = productDAO.findProducts(search, supplierId, isActive, minPurchasePrice, minSalePrice, page, pageSize);
        int totalProducts = productDAO.getTotalFilteredProducts(search, supplierId, isActive, minPurchasePrice, minSalePrice);
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);
        List<Supplier> suppliers = supplierDAO.findAll();

        req.setAttribute("products", products);
        req.setAttribute("suppliers", suppliers);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalProducts", totalProducts);
        req.setAttribute("searchTerm", search);
        req.setAttribute("supplierId", req.getParameter("supplierId"));
        req.setAttribute("isActive", req.getParameter("isActive"));
        req.setAttribute("minPurchasePrice", req.getParameter("minPurchasePrice"));
        req.setAttribute("minSalePrice", req.getParameter("minSalePrice"));

        req.getRequestDispatcher("/admin/product/product.jsp").forward(req, resp);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        req.setAttribute("suppliers", supplierDAO.findAll());
        req.getRequestDispatcher("/admin/product/addProduct.jsp").forward(req, resp);
    }

    private void createProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Product product = extractProductFromRequest(req, false);
            int id = productDAO.insert(product);
            if (id > 0) {
                setToast(req, "Tạo sản phẩm thành công!", "success");
            } else {
                setToast(req, "Lỗi tạo sản phẩm.", "error");
            }
        } catch (Exception e) {
            setToast(req, "Lỗi: " + e.getMessage(), "error");
        }
        resp.sendRedirect(req.getContextPath() + "/admin/manage-product?action=list");
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        Integer id = parseIntOrNull(req.getParameter("id"));
        if (id == null) {
            setToast(req, "ID sản phẩm không hợp lệ.", "error");
            resp.sendRedirect(req.getContextPath() + "/admin/manage-product?action=list");
            return;
        }

        Product product = productDAO.findById(id);
        if (product == null) {
            setToast(req, "Sản phẩm không tồn tại.", "error");
            resp.sendRedirect(req.getContextPath() + "/admin/manage-product?action=list");
            return;
        }

        req.setAttribute("product", product);
        req.setAttribute("suppliers", supplierDAO.findAll());
        req.getRequestDispatcher("/admin/edit-product.jsp").forward(req, resp);
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Product product = extractProductFromRequest(req, true);
            boolean updated = productDAO.update(product);
            if (updated) {
                setToast(req, "Cập nhật sản phẩm thành công!", "success");
            } else {
                setToast(req, "Lỗi cập nhật sản phẩm.", "error");
            }
        } catch (Exception e) {
            setToast(req, "Lỗi: " + e.getMessage(), "error");
        }
        resp.sendRedirect(req.getContextPath() + "/admin/manage-product?action=list");
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        Integer id = parseIntOrNull(req.getParameter("id"));
        if (id == null) {
            setToast(req, "ID sản phẩm không hợp lệ.", "error");
            resp.sendRedirect(req.getContextPath() + "/admin/manage-product?action=list");
            return;
        }
        boolean deleted = productDAO.delete(id);
        if (deleted) {
            setToast(req, "Xóa sản phẩm thành công!", "success");
        } else {
            setToast(req, "Lỗi xóa sản phẩm.", "error");
        }
        resp.sendRedirect(req.getContextPath() + "/admin/manage-product?action=list");
    }

    private Product extractProductFromRequest(HttpServletRequest req, boolean isUpdate) {
        Integer id = isUpdate ? parseIntOrNull(req.getParameter("productId")) : null;
        String productCode = req.getParameter("productCode");
        String productName = req.getParameter("productName");
        String description = req.getParameter("description");
        String unit = req.getParameter("unit");
        Float purchasePrice = parseFloatOrNull(req.getParameter("purchasePrice"));
        Float salePrice = parseFloatOrNull(req.getParameter("salePrice"));
        Integer supplierId = parseIntOrNull(req.getParameter("supplierId"));
        Integer lowStockThreshold = parseIntOrNull(req.getParameter("lowStockThreshold"));
        Boolean isActive = parseBooleanOrNull(req.getParameter("isActive"));

        Product p = new Product();
        if (isUpdate) {
            p.setProductId(id);
        }
        p.setProductCode(productCode);
        p.setProductName(productName);
        p.setDescription(description);
        p.setUnit(unit);
        p.setPurchasePrice(purchasePrice);
        p.setSalePrice(salePrice);
        p.setSupplierId(supplierId);
        p.setLowStockThreshold(lowStockThreshold);
        p.setIsActive(isActive);
        return p;
    }

    private Integer parseIntOrNull(String s) {
        try {
            if (s == null || s.isEmpty()) return null;
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private int parseIntOrDefault(String s, int defaultValue) {
        try {
            if (s == null || s.isEmpty()) return defaultValue;
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private Boolean parseBooleanOrNull(String s) {
        if (s == null || s.isEmpty()) return null;
        return Boolean.parseBoolean(s);
    }

    private Float parseFloatOrNull(String s) {
        try {
            if (s == null || s.isEmpty()) return null;
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void setToast(HttpServletRequest req, String message, String type) {
        HttpSession session = req.getSession();
        session.setAttribute("toastMessage", message);
        session.setAttribute("toastType", type);
    }
}
