package controller;

import dao.UserDAO;
import model.User;
import model.Role;
import utils.PasswordUtil;
import utils.SessionUtil;
import context.DBContext;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection conn = new DBContext().getConnection();
            userDAO = new UserDAO(conn);
        } catch (Exception e) {
            throw new ServletException("Không thể khởi tạo UserDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User currentUser = SessionUtil.getUserFromSession(request);
        if (currentUser == null) {
            SessionUtil.setErrorMessage(request, "Vui lòng đăng nhập để xem profile");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = userDAO.getUserById(currentUser.getUserId());
        if (user == null) {
            SessionUtil.setErrorMessage(request, "Không tìm thấy thông tin người dùng");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User currentUser = SessionUtil.getUserFromSession(request);
        if (currentUser == null) {
            SessionUtil.setErrorMessage(request, "Vui lòng đăng nhập để cập nhật profile");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        if ("updateProfile".equals(action)) {
            updateProfile(request, response, currentUser);
        } else if ("changePassword".equals(action)) {
            changePassword(request, response, currentUser);
        } else {
            SessionUtil.setErrorMessage(request, "Hành động không hợp lệ");
            response.sendRedirect(request.getContextPath() + "/profile");
        }
    }

    private void updateProfile(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws IOException {

        String fullName = request.getParameter("fullName");
        String roleStr = request.getParameter("role");

        if (fullName == null || fullName.trim().isEmpty()) {
            SessionUtil.setErrorMessage(request, "Vui lòng nhập họ tên");
            response.sendRedirect(request.getContextPath() + "/profile");
            return;
        }

        try {
            Role role = Role.fromString(roleStr);
            currentUser.setFullName(fullName.trim());
            currentUser.setRoleId(role.getRoleId());
            boolean success = userDAO.update(currentUser);

            if (success) {
                SessionUtil.setUserInSession(request, currentUser);
                SessionUtil.setSuccessMessage(request, "Cập nhật thông tin thành công");
            } else {
                SessionUtil.setErrorMessage(request, "Có lỗi xảy ra khi cập nhật thông tin");
            }

        } catch (IllegalArgumentException e) {
            SessionUtil.setErrorMessage(request, "Vai trò không hợp lệ");
        } catch (Exception e) {
            e.printStackTrace();
            SessionUtil.setErrorMessage(request, "Có lỗi xảy ra khi cập nhật thông tin");
        }
        response.sendRedirect(request.getContextPath() + "/profile");
    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws IOException {

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (currentPassword == null || currentPassword.trim().isEmpty() ||
            newPassword == null || newPassword.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty()) {

            SessionUtil.setErrorMessage(request, "Vui lòng điền đầy đủ thông tin mật khẩu");
            response.sendRedirect(request.getContextPath() + "/profile");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            SessionUtil.setErrorMessage(request, "Mật khẩu mới xác nhận không khớp");
            response.sendRedirect(request.getContextPath() + "/profile");
            return;
        }

        if (!PasswordUtil.isValidPassword(newPassword)) {
            SessionUtil.setErrorMessage(request, "Mật khẩu mới phải có ít nhất 6 ký tự, bao gồm chữ và số");
            response.sendRedirect(request.getContextPath() + "/profile");
            return;
        }

        try {
            if (!PasswordUtil.verifyPassword(currentPassword, currentUser.getPasswordHash())) {
                SessionUtil.setErrorMessage(request, "Mật khẩu hiện tại không chính xác");
                response.sendRedirect(request.getContextPath() + "/profile");
                return;
            }
            String newHashedPassword = PasswordUtil.hashPassword(newPassword);
            boolean success = userDAO.updatePassword(currentUser.getUserId(), newHashedPassword);

            if (success) {
                SessionUtil.setSuccessMessage(request, "Đổi mật khẩu thành công");
            } else {
                SessionUtil.setErrorMessage(request, "Có lỗi xảy ra khi đổi mật khẩu");
            }

        } catch (Exception e) {
            e.printStackTrace();
            SessionUtil.setErrorMessage(request, "Có lỗi xảy ra khi đổi mật khẩu");
        }

        response.sendRedirect(request.getContextPath() + "/profile");
    }
}
