package controller;

import dao.UserDAO;
import utils.PasswordUtil;
import utils.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "ResetPasswordServlet", urlPatterns = {"/reset-password"})
public class ResetPasswordServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Boolean otpVerified = (Boolean) session.getAttribute("otpVerified");
        String resetEmail = (String) session.getAttribute("resetEmail");

        if (otpVerified == null || !otpVerified || resetEmail == null) {
            SessionUtil.setErrorMessage(request, "Vui lòng hoàn thành xác thực OTP trước");
            response.sendRedirect(request.getContextPath() + "/forgot-password");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/reset-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Boolean otpVerified = (Boolean) session.getAttribute("otpVerified");
        Integer userId = (Integer) session.getAttribute("resetUserId");

        if (otpVerified == null || !otpVerified || userId == null) {
            SessionUtil.setErrorMessage(request, "Phiên làm việc không hợp lệ. Vui lòng thử lại");
            response.sendRedirect(request.getContextPath() + "/forgot-password");
            return;
        }

        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (newPassword == null || newPassword.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty()) {
            SessionUtil.setErrorMessage(request, "Vui lòng nhập đầy đủ thông tin");
            response.sendRedirect(request.getContextPath() + "/reset-password");
            return;
        }

        if (newPassword.length() < 6) {
            SessionUtil.setErrorMessage(request, "Mật khẩu phải có ít nhất 6 ký tự");
            response.sendRedirect(request.getContextPath() + "/reset-password");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            SessionUtil.setErrorMessage(request, "Mật khẩu xác nhận không khớp");
            response.sendRedirect(request.getContextPath() + "/reset-password");
            return;
        }

        try {
            String hashedPassword = PasswordUtil.hashPassword(newPassword);
            boolean updated = userDAO.updatePassword(userId, hashedPassword);

            if (updated) {
                session.removeAttribute("resetOTP");
                session.removeAttribute("resetEmail");
                session.removeAttribute("resetUserId");
                session.removeAttribute("otpVerified");
                session.removeAttribute("otpGeneratedTime");

                SessionUtil.setSuccessMessage(request, "Đặt lại mật khẩu thành công! Vui lòng đăng nhập với mật khẩu mới");
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                SessionUtil.setErrorMessage(request, "Có lỗi xảy ra khi cập nhật mật khẩu. Vui lòng thử lại");
                response.sendRedirect(request.getContextPath() + "/reset-password");
            }

        } catch (Exception e) {
            System.out.println("Error in reset password: " + e.getMessage());
            e.printStackTrace();
            SessionUtil.setErrorMessage(request, "Có lỗi xảy ra trong hệ thống");
            response.sendRedirect(request.getContextPath() + "/reset-password");
        }
    }
}
