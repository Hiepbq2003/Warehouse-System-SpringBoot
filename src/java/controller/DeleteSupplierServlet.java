package controller;

import context.DBContext;
import dao.SupplierDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "DeleteSupplierServlet", urlPatterns = {"/DeleteSupplierServlet"})
public class DeleteSupplierServlet extends HttpServlet {

    private SupplierDAO supplierDAO;
    private DBContext dbContext;

    @Override
    public void init() throws ServletException {
        try {
            dbContext = new DBContext(); // Tạo đối tượng DBContext
            Connection conn = dbContext.getConnection(); // Lấy connection từ đối tượng
            supplierDAO = new SupplierDAO(conn);
        } catch (SQLException e) {
            throw new ServletException("Không thể kết nối DB", e);
        }
    }

    @Override
    public void destroy() {
        if (dbContext != null) {
            dbContext.close(); // Đóng kết nối khi servlet bị huỷ
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String supplierIdStr = req.getParameter("supplierId");
        if (supplierIdStr != null && !supplierIdStr.isEmpty()) {
            try {
                int supplierId = Integer.parseInt(supplierIdStr);
                boolean deleted = supplierDAO.deleteSupplier(supplierId);
                if (deleted) {
                    req.getSession().setAttribute("message", "Xóa nhà cung cấp thành công.");
                } else {
                    req.getSession().setAttribute("message", "Xóa nhà cung cấp thất bại.");
                }
            } catch (NumberFormatException e) {
                req.getSession().setAttribute("message", "ID nhà cung cấp không hợp lệ.");
            }
        } else {
            req.getSession().setAttribute("message", "Không tìm thấy nhà cung cấp để xóa.");
        }
        resp.sendRedirect("suppliers"); // Chuyển hướng về trang danh sách
    }
}
