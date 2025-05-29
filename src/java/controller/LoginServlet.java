package controller;

import context.DBContext;
import dao.UserDAO;
import model.User;
import utils.PasswordUtil;
import utils.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private DBContext dbContext;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            dbContext = new DBContext();
            userDAO = new UserDAO(dbContext.getConnection());
        } catch (Exception e) {
            throw new ServletException("Không thể kết nối cơ sở dữ liệu", e);
        }
    }

    @Override
    public void destroy() {
        try {
            if (dbContext != null) dbContext.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (SessionUtil.isUserLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }
        request.getRequestDispatcher("login/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            SessionUtil.setErrorMessage(request, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            User user = userDAO.findByUsername(username.trim());

            if (user == null) {
                SessionUtil.setErrorMessage(request, "Tên đăng nhập không tồn tại");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            if (!PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
                SessionUtil.setErrorMessage(request, "Mật khẩu không chính xác");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            if (!user.isActive()) {
                SessionUtil.setErrorMessage(request, "Tài khoản đã bị vô hiệu hóa");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            SessionUtil.setUserInSession(request, user);
            SessionUtil.setSuccessMessage(request, "Đăng nhập thành công! Chào mừng " + user.getFullName());
            response.sendRedirect(request.getContextPath() + "/dashboard");

        } catch (Exception e) {
            e.printStackTrace();
            SessionUtil.setErrorMessage(request, "Lỗi khi đăng nhập");
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
