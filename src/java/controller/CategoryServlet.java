package controller;

import context.DBContext;
import dao.CategoryDAO;
import model.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "CategoryServlet", urlPatterns = {"/categories"})
public class CategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        DBContext dbContext = null;

        try {
            dbContext = new DBContext();
            Connection conn = dbContext.getConnection();

            CategoryDAO categoryDAO = new CategoryDAO(conn);
            List<Category> categoryList = categoryDAO.getAllCategories();

            req.setAttribute("categoryList", categoryList);
            req.getRequestDispatcher("/category/manageCategory.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("❌ Lỗi khi tải danh sách danh mục: " + e.getMessage());
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
