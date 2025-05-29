package controller;

import context.DBContext;
import dao.CategoryDAO;
import model.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "AddCategoryServlet", urlPatterns = {"/AddCategoryServlet"})
public class AddCategoryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        DBContext dbContext = null;

        try {
            // ❌ KHÔNG cần lấy categoryId vì IDENTITY tự tăng
            String categoryName = request.getParameter("categoryName");

            // Tạo đối tượng Category (chỉ cần tên)
            Category category = new Category();
            category.setCategoryName(categoryName);

            // Thêm vào DB
            dbContext = new DBContext();
            boolean success = new CategoryDAO(dbContext.getConnection()).addCategory(category);

            if (success) {
                response.sendRedirect("categories"); // Trang danh sách category
            } else {
                request.setAttribute("error", "❌ Không thể thêm danh mục.");
                request.getRequestDispatcher("/category/manageCategory.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "❌ Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("/category/manageCategory.jsp").forward(request, response);
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
