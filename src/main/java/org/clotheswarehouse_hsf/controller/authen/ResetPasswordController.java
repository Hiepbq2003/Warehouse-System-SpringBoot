package org.clotheswarehouse_hsf.controller.authen;

import jakarta.servlet.http.HttpSession;
import org.clotheswarehouse_hsf.model.User;
import org.clotheswarehouse_hsf.service.UserService;
import org.clotheswarehouse_hsf.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reset-password")
public class ResetPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordUtil passwordUtil;

    @GetMapping
    public String showResetForm(HttpSession session, Model model) {
        if (!isSessionValid(session)) {
            session.setAttribute("errorMessage", "Vui lòng hoàn thành xác thực OTP trước");
            return "redirect:/forgot-password";
        }
        return "authen/reset-password";
    }

    @PostMapping
    public String resetPassword(@RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                HttpSession session) {

        if (!isSessionValid(session)) {
            session.setAttribute("errorMessage", "Phiên làm việc không hợp lệ");
            return "redirect:/forgot-password";
        }

        if (newPassword == null || newPassword.isBlank() ||
                confirmPassword == null || confirmPassword.isBlank()) {
            session.setAttribute("errorMessage", "Vui lòng nhập đầy đủ mật khẩu");
            return "redirect:/reset-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            session.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp");
            return "redirect:/reset-password";
        }

        if (!passwordUtil.isValidPassword(newPassword)) {
            session.setAttribute("errorMessage", "Mật khẩu phải có ít nhất 6 ký tự, gồm chữ và số");
            return "redirect:/reset-password";
        }

        Integer userId = (Integer) session.getAttribute("resetUserId");
        if (userId == null) {
            session.setAttribute("errorMessage", "Không tìm thấy người dùng");
            return "redirect:/reset-password";
        }

        User user = userService.findById(userId);
        if (user == null) {
            session.setAttribute("errorMessage", "Không tìm thấy người dùng");
            return "redirect:/reset-password";
        }

        // Cập nhật mật khẩu đã hash
        user.setPasswordHash(passwordUtil.hashPassword(newPassword));
        userService.save(user);

        // Xóa thông tin session
        clearResetSession(session);

        session.setAttribute("successMessage", "Đặt lại mật khẩu thành công. Đăng nhập lại.");
        return "redirect:/login";
    }

    private boolean isSessionValid(HttpSession session) {
        Boolean otpVerified = (Boolean) session.getAttribute("otpVerified");
        Integer userId = (Integer) session.getAttribute("resetUserId");
        return otpVerified != null && otpVerified && userId != null;
    }

    private void clearResetSession(HttpSession session) {
        session.removeAttribute("resetUserId");
        session.removeAttribute("resetEmail");
        session.removeAttribute("resetOTP");
        session.removeAttribute("otpVerified");
    }
}
