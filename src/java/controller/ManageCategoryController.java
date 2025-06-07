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
public class ManageCategoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                handleListCategories(req, resp);
                break;
            case "showForm":
                handleShowForm(req, resp);
                break;
            default:
                handleListCategories(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "add":
                handleAddCategory(req, resp);
                break;
            case "edit":
                handleEditCategory(req, resp);
                break;
            case "delete":
                handleDeleteCategory(req, resp);
                break;
            default:
                handleListCategories(req, resp);
                break;
        }
    }

    private void handleListCategories(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        DBContext dbContext = null;

        try {
            dbContext = new DBContext();
            Connection conn = dbContext.getConnection();

            CategoryDAO categoryDAO = new CategoryDAO();
            List<Category> categoryList = categoryDAO.getAllCategories();

            req.setAttribute("categoryList", categoryList);
            req.getRequestDispatcher("/category/manageCategory.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("❌ Lỗi khi tải danh sách danh mục: " + e.getMessage());
        } finally {
            closeDBContext(dbContext);
        }
    }

    private void handleShowForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/category/manageCategory.jsp").forward(req, resp);
    }

    private void handleAddCategory(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        DBContext dbContext = null;

        try {
            String categoryName = req.getParameter("categoryName");

            Category category = new Category();
            category.setCategoryName(categoryName);

            dbContext = new DBContext();
            boolean success = new CategoryDAO().addCategory(category);

            if (success) {
                resp.sendRedirect("categories");
            } else {
                req.setAttribute("error", " Không thể thêm danh mục.");
                req.getRequestDispatcher("/category/manageCategory.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", " Lỗi hệ thống: " + e.getMessage());
            req.getRequestDispatcher("/category/manageCategory.jsp").forward(req, resp);
        } finally {
            closeDBContext(dbContext);
        }
    }

    private void handleEditCategory(HttpServletRequest req, HttpServletResponse resp)
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

            CategoryDAO categoryDAO = new CategoryDAO();

            Category category = categoryDAO.getCategoryById(categoryId);
            if (category == null) {
                resp.sendRedirect("categories");
                return;
            }

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
            closeDBContext(dbContext);
        }
    }

    private void handleDeleteCategory(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String categoryIdStr = req.getParameter("categoryId");
        DBContext dbContext = null;

        try {
            dbContext = new DBContext();
            Connection conn = dbContext.getConnection();

            CategoryDAO categoryDAO = new CategoryDAO();

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
            closeDBContext(dbContext);
        }

        resp.sendRedirect("categories");
    }

    private void closeDBContext(DBContext dbContext) {
        if (dbContext != null) {
            try {
                dbContext.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
