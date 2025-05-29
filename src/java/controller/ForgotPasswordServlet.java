package controller;

import context.DBContext;
import dao.UserDAO;
import model.User;
import utils.EmailUtil;
import utils.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.Random;

@WebServlet(name = "ForgotPasswordServlet", urlPatterns = {"/forgot-password"})
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/forgot-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        if (email == null || email.trim().isEmpty()) {
            SessionUtil.setErrorMessage(request, "Vui lòng nhập địa chỉ email");
            response.sendRedirect(request.getContextPath() + "/forgot-password");
            return;
        }

        DBContext dbContext = null;
        Connection conn = null;

        try {
            dbContext = new DBContext();
            conn = dbContext.getConnection();
            UserDAO userDAO = new UserDAO(conn);

            User user = userDAO.findByEmail(email.trim());
            if (user == null) {
                SessionUtil.setErrorMessage(request, "Email không tồn tại trong hệ thống");
                response.sendRedirect(request.getContextPath() + "/forgot-password");
                return;
            }

            String otp = generateOTP();
            HttpSession session = request.getSession();
            session.setAttribute("resetOTP", otp);
            session.setAttribute("resetEmail", email.trim());
            session.setAttribute("resetUserId", user.getUserId());
            session.setAttribute("otpGeneratedTime", System.currentTimeMillis());

            boolean emailSent = EmailUtil.sendOTP(email, otp, user.getFullName());
            if (emailSent) {
                SessionUtil.setSuccessMessage(request, "Mã OTP đã được gửi đến email của bạn");
                response.sendRedirect(request.getContextPath() + "/verify-otp");
            } else {
                SessionUtil.setErrorMessage(request, "Có lỗi xảy ra khi gửi email. Vui lòng thử lại");
                response.sendRedirect(request.getContextPath() + "/forgot-password");
            }

        } catch (Exception e) {
            e.printStackTrace();
            SessionUtil.setErrorMessage(request, "Có lỗi xảy ra trong hệ thống");
            response.sendRedirect(request.getContextPath() + "/forgot-password");
        } finally {
            try {
                if (conn != null && !conn.isClosed()) conn.close();
                if (dbContext != null) dbContext.close();
            } catch (Exception ignored) {}
        }
    }

    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
