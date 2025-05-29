package controller;

import context.DBContext;
import dao.UserDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "EditUserServlet", urlPatterns = {"/EditUserServlet"})
public class EditUserServlet extends HttpServlet {

    private UserDAO userDAO;
    private Connection conn;

    @Override
    public void init() throws ServletException {
        try {
            DBContext dbContext = new DBContext(); // tạo đối tượng DBContext
            conn = dbContext.getConnection();     // lấy connection từ đối tượng
            userDAO = new UserDAO(conn);
        } catch (SQLException e) {
            throw new ServletException("Không thể kết nối DB", e);
        }
    }

    @Override
    public void destroy() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(req.getParameter("userId"));
            String fullName = req.getParameter("fullName");
            String email = req.getParameter("email");
            int roleId = Integer.parseInt(req.getParameter("role"));
            String isActiveStr = req.getParameter("isActive");
            boolean isActive = "on".equalsIgnoreCase(isActiveStr) || "true".equalsIgnoreCase(isActiveStr);

            User user = userDAO.getUserById(userId);
            if (user == null) {
                resp.sendRedirect("UserServlet");
                return;
            }

            user.setFullName(fullName);
            user.setEmail(email);
            user.setRoleId(roleId);
            user.setActive(isActive);

            boolean updated = userDAO.update(user);
            if (updated) {
                resp.sendRedirect("UserServlet");
            } else {
                req.setAttribute("error", "Cập nhật thất bại!");
                req.setAttribute("user", user);
                req.getRequestDispatcher("/manageUser/ManageUser.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/manageUser/ManageUser.jsp");
        }
    }
}
