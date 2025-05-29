package controller;

import context.DBContext;
import dao.SupplierDAO;
import model.Supplier;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "EditSupplierServlet", urlPatterns = {"/EditSupplierServlet"})
public class EditSupplierServlet extends HttpServlet {

    private SupplierDAO supplierDAO;
    private DBContext dbContext;

    @Override
    public void init() throws ServletException {
        try {
            dbContext = new DBContext();
            Connection conn = dbContext.getConnection();
            supplierDAO = new SupplierDAO(conn);
        } catch (SQLException e) {
            throw new ServletException("❌ Không thể kết nối DB", e);
        }
    }

    @Override
    public void destroy() {
        if (dbContext != null) {
            dbContext.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        try {
            int supplierId = Integer.parseInt(req.getParameter("supplierId"));
            String name = req.getParameter("name");
            String contactPerson = req.getParameter("contactPerson");
            String phone = req.getParameter("phone");
            String email = req.getParameter("email");
            String address = req.getParameter("address");

            Supplier supplier = supplierDAO.getSupplierById(supplierId);
            if (supplier == null) {
                resp.sendRedirect("suppliers");
                return;
            }

            supplier.setSupplierName(name);
            supplier.setContactPerson(contactPerson);
            supplier.setPhoneNumber(phone);
            supplier.setEmail(email);
            supplier.setAddress(address);

            boolean updated = supplierDAO.updateSupplier(supplier);
            if (updated) {
                resp.sendRedirect("suppliers");
            } else {
                req.setAttribute("error", "❌ Cập nhật nhà cung cấp thất bại!");
                req.setAttribute("supplier", supplier);
                req.getRequestDispatcher("manageSupplier.jsp").forward(req, resp);
            }
        } catch (NumberFormatException e) {
            req.setAttribute("error", "❌ ID nhà cung cấp không hợp lệ.");
            req.getRequestDispatcher("/supplier/manageSupplier.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "❌ Đã xảy ra lỗi: " + e.getMessage());
            req.getRequestDispatcher("/supplier/manageSupplier.jsp").forward(req, resp);
        }
    }
}
