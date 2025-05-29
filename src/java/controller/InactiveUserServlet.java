package controller;

import context.DBContext;
import dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "InactiveUserServlet", urlPatterns = {"/InactiveUserServlet"})
public class InactiveUserServlet extends HttpServlet {

    private DBContext dbContext;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            dbContext = new DBContext();
            userDAO = new UserDAO(dbContext.getConnection());
        } catch (SQLException e) {
            throw new ServletException("Không thể kết nối DB", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String userIdStr = req.getParameter("userId");
        if (userIdStr != null && !userIdStr.isEmpty()) {
            try {
                int userId = Integer.parseInt(userIdStr);
                boolean success;
                success = userDAO.inactive(userId);
                if (success) {
                    req.getSession().setAttribute("message", "Người dùng đã bị vô hiệu hóa.");
                } else {
                    req.getSession().setAttribute("message", "Không thể vô hiệu hóa người dùng.");
                }
            } catch (NumberFormatException e) {
                req.getSession().setAttribute("message", "ID người dùng không hợp lệ.");
            }
        } else {
            req.getSession().setAttribute("message", "Không tìm thấy người dùng.");
        }
        resp.sendRedirect("UserServlet");
    }

    @Override
    public void destroy() {
        if (dbContext != null) {
            dbContext.close();
        }
    }
}
