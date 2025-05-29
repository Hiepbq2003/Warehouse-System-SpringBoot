package controller;

import context.DBContext;
import dao.SupplierDAO;
import model.Supplier;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;

@WebServlet(name = "AddSupplierServlet", urlPatterns = {"/AddSupplierServlet"})
public class AddSupplierServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String contactPerson = request.getParameter("contactPerson");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        Supplier supplier = new Supplier();
        supplier.setSupplierName(name);
        supplier.setContactPerson(contactPerson);
        supplier.setPhoneNumber(phone);
        supplier.setEmail(email);
        supplier.setAddress(address);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        supplier.setCreatedAt(now);
        supplier.setUpdatedAt(now);

        DBContext dbContext = null;
        Connection conn = null;

        try {
            dbContext = new DBContext();
            conn = dbContext.getConnection();

            SupplierDAO supplierDAO = new SupplierDAO(conn);
            boolean success = supplierDAO.addSupplier(supplier);

            if (success) {
                response.sendRedirect("suppliers"); // Redirect về danh sách nhà cung cấp
            } else {
                request.setAttribute("error", "❌ Không thể thêm nhà cung cấp.");
                request.getRequestDispatcher("/supplier/manageSupplier.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "❌ Lỗi kết nối DB.");
            request.getRequestDispatcher("/supplier/manageSupplier.jsp").forward(request, response);
        } finally {
            if (dbContext != null) {
                dbContext.close();
            }
        }
    }
}
