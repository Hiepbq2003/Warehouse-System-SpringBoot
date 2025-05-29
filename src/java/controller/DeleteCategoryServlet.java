package controller;

import context.DBContext;
import dao.CategoryDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "DeleteCategoryServlet", urlPatterns = {"/DeleteCategoryServlet"})
public class DeleteCategoryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String categoryIdStr = req.getParameter("categoryId");

        DBContext dbContext = null;

        try {
            dbContext = new DBContext();
            Connection conn = dbContext.getConnection();

            CategoryDAO categoryDAO = new CategoryDAO(conn);

            if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
                try {
                    int categoryId = Integer.parseInt(categoryIdStr);
                    boolean deleted = categoryDAO.deleteCategory(categoryId);

                    if (deleted) {
                        req.getSession().setAttribute("message", "✅ Xóa danh mục thành công.");
                    } else {
                        req.getSession().setAttribute("message", "❌ Không tìm thấy danh mục để xóa.");
                    }
                } catch (NumberFormatException e) {
                    req.getSession().setAttribute("message", "❌ ID danh mục không hợp lệ.");
                }
            } else {
                req.getSession().setAttribute("message", "❌ Không tìm thấy danh mục để xóa.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("message", "❌ Lỗi hệ thống: " + e.getMessage());
        } finally {
            if (dbContext != null) {
                try {
                    dbContext.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        resp.sendRedirect("categories");
    }
}
