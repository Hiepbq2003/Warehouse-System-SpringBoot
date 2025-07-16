package org.clotheswarehouse_hsf.controller.authen;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.clotheswarehouse_hsf.model.User;
import org.clotheswarehouse_hsf.service.UserService;
import org.clotheswarehouse_hsf.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@Controller
@RequestMapping("/verify-otp")
public class VerifyOTPController {

    private static final long OTP_VALIDITY_DURATION = 2 * 60 * 1000; // 2 phút

    @Autowired
    private UserService userService;

    @GetMapping
    public String showOTPForm(HttpSession session, Model model) {
        if (!isOTPSessionValid(session)) {
            SessionUtil.setErrorMessage(session, "Phiên làm việc hết hạn");
            return "redirect:/forgot-password";
        }
        return "authen/verify-otp";
    }

    @GetMapping(params = "action=resend")
    public String resendOTP(HttpSession session) {
        if (!isOTPSessionValid(session)) {
            SessionUtil.setErrorMessage(session, "Phiên làm việc hết hạn");
            return "redirect:/forgot-password";
        }
        SessionUtil.setErrorMessage(session, "Vui lòng yêu cầu gửi lại mã OTP");
        return "redirect:/forgot-password";
    }

    @PostMapping
    public String verifyOTP(@RequestParam(required = false) String otpCode,
                            HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (!isOTPSessionAndExpiryValid(session)) {
            SessionUtil.setErrorMessage(session, "OTP hết hạn hoặc sai thông tin");
            return "redirect:/forgot-password";
        }

        String enteredOTP = extractOTPFromRequest(request, otpCode);
        if (!enteredOTP.matches("\\d{6}")) {
            SessionUtil.setErrorMessage(session, "Vui lòng nhập đúng 6 chữ số");
            return "redirect:/verify-otp";
        }

        String storedOTP = (String) session.getAttribute("resetOTP");
        if (!enteredOTP.equals(storedOTP)) {
            SessionUtil.setErrorMessage(session, "Mã OTP không chính xác");
            return "redirect:/verify-otp";
        }

        session.setAttribute("otpVerified", true);

        String email = (String) session.getAttribute("resetEmail");
        User user = userService.findByEmail(email);
        if (user != null) {
            session.setAttribute("resetUserId", user.getUserId());
        }

        SessionUtil.setSuccessMessage(session, "Xác thực thành công! Đặt lại mật khẩu");
        return "redirect:/reset-password";
    }

    private boolean isOTPSessionValid(HttpSession session) {
        return session.getAttribute("resetEmail") != null;
    }

    private boolean isOTPSessionAndExpiryValid(HttpSession session) {
        String otp = (String) session.getAttribute("resetOTP");
        String email = (String) session.getAttribute("resetEmail");
        Long createdAt = (Long) session.getAttribute("otpGeneratedTime");

        if (otp == null || email == null || createdAt == null) return false;

        long now = Instant.now().toEpochMilli();
        return now - createdAt <= OTP_VALIDITY_DURATION;
    }

    private String extractOTPFromRequest(HttpServletRequest request, String otpCode) {
        if (otpCode != null && !otpCode.isEmpty()) return otpCode;

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 6; i++) {
            String digit = request.getParameter("otp" + i);
            if (digit != null && !digit.isEmpty()) {
                sb.append(digit.trim());
            }
        }
        return sb.toString();
    }
}
