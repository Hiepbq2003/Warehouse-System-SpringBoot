package controller;

import context.DBContext;
import dao.CategoryDAO;
import model.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "EditCategoryServlet", urlPatterns = {"/EditCategoryServlet"})
public class EditCategoryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        DBContext dbContext = null;

        try {
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            String categoryName = req.getParameter("categoryName");

            dbContext = new DBContext();
            Connection conn = dbContext.getConnection();

            CategoryDAO categoryDAO = new CategoryDAO(conn);

            Category category = categoryDAO.getCategoryById(categoryId);
            if (category == null) {
                resp.sendRedirect("categories"); // danh mục không tồn tại
                return;
            }

            // Cập nhật tên danh mục (không thay đổi ID)
            category.setCategoryName(categoryName);
            boolean updated = categoryDAO.updateCategory(category);

            if (updated) {
                resp.sendRedirect("categories");
            } else {
                req.setAttribute("error", "❌ Cập nhật danh mục thất bại!");
                req.setAttribute("category", category);
                req.getRequestDispatcher("/category/manageCategory.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "❌ Đã xảy ra lỗi: " + e.getMessage());
            req.getRequestDispatcher("/category/manageCategory.jsp").forward(req, resp);
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
