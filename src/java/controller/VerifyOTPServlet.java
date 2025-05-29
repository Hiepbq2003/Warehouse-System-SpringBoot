package controller;

import utils.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "VerifyOTPServlet", urlPatterns = {"/verify-otp"})
public class VerifyOTPServlet extends HttpServlet {

    private static final long OTP_VALIDITY_DURATION = 5 * 60 * 1000; // 5 minutes

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String resetEmail = (String) session.getAttribute("resetEmail");

        if (resetEmail == null) {
            SessionUtil.setErrorMessage(request, "Phiên làm việc đã hết hạn. Vui lòng thử lại");
            response.sendRedirect(request.getContextPath() + "/forgot-password");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/verify-otp.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String storedOTP = (String) session.getAttribute("resetOTP");
        String resetEmail = (String) session.getAttribute("resetEmail");
        Long otpGeneratedTime = (Long) session.getAttribute("otpGeneratedTime");

        if (storedOTP == null || resetEmail == null || otpGeneratedTime == null) {
            SessionUtil.setErrorMessage(request, "Phiên làm việc đã hết hạn. Vui lòng thử lại");
            response.sendRedirect(request.getContextPath() + "/forgot-password");
            return;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - otpGeneratedTime > OTP_VALIDITY_DURATION) {
            SessionUtil.setErrorMessage(request, "Mã OTP đã hết hạn. Vui lòng yêu cầu mã mới");
            response.sendRedirect(request.getContextPath() + "/forgot-password");
            return;
        }

        String enteredOTP = request.getParameter("otpCode");
        if (enteredOTP == null || enteredOTP.trim().isEmpty()) {
            // Try to get from individual inputs
            StringBuilder otpBuilder = new StringBuilder();
            for (int i = 1; i <= 6; i++) {
                String digit = request.getParameter("otp" + i);
                if (digit != null && !digit.trim().isEmpty()) {
                    otpBuilder.append(digit.trim());
                }
            }
            enteredOTP = otpBuilder.toString();
        }

        if (enteredOTP == null || enteredOTP.length() != 6) {
            SessionUtil.setErrorMessage(request, "Vui lòng nhập đầy đủ 6 chữ số");
            response.sendRedirect(request.getContextPath() + "/verify-otp");
            return;
        }

        if (!enteredOTP.equals(storedOTP)) {
            SessionUtil.setErrorMessage(request, "Mã OTP không chính xác. Vui lòng thử lại");
            response.sendRedirect(request.getContextPath() + "/verify-otp");
            return;
        }

        session.setAttribute("otpVerified", true);
        SessionUtil.setSuccessMessage(request, "Xác thực thành công! Vui lòng đặt mật khẩu mới");
        response.sendRedirect(request.getContextPath() + "/reset-password");
    }
}
