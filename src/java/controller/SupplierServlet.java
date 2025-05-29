package controller;

import context.DBContext;
import dao.SupplierDAO;
import model.Supplier;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "SupplierServlet", urlPatterns = {"/suppliers"})
public class SupplierServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        DBContext dbContext = null;

        try {
            dbContext = new DBContext();
            Connection conn = dbContext.getConnection();

            SupplierDAO supplierDAO = new SupplierDAO(conn);
            List<Supplier> supplierList = supplierDAO.getAllSuppliers();

            req.setAttribute("supplierList", supplierList);
            req.getRequestDispatcher("/supplier/manageSupplier.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("Lỗi khi tải danh sách nhà cung cấp." + e.getMessage());
        } finally {
            if (dbContext != null) {
                try {
                    dbContext.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
