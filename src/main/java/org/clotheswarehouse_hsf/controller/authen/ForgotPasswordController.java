package org.clotheswarehouse_hsf.controller.authen;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.clotheswarehouse_hsf.model.User;
import org.clotheswarehouse_hsf.service.UserService;
import org.clotheswarehouse_hsf.utils.EmailUtil;
import org.clotheswarehouse_hsf.utils.RegexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;

@Controller
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailUtil emailUtil;

    @GetMapping
    public String showForgotPasswordForm() {
        return "authen/forgot-password";
    }

    @PostMapping
    public String handleSendOTP(@RequestParam("email") String emailInput,
                                HttpServletRequest request,
                                RedirectAttributes redirect) {

        String email = emailInput == null ? "" : emailInput.trim().toLowerCase();

        if (email.isEmpty()) {
            redirect.addFlashAttribute("errorMessage", "Vui lòng nhập địa chỉ email");
            return "redirect:/forgot-password";
        }

        if (!RegexUtil.isValidEmail(email)) {
            redirect.addFlashAttribute("errorMessage", "Định dạng email không hợp lệ");
            return "redirect:/forgot-password";
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            redirect.addFlashAttribute("errorMessage", "Email không tồn tại trong hệ thống");
            return "redirect:/forgot-password";
        }

        String otp = generateOTP();

        HttpSession session = request.getSession();
        session.setAttribute("resetOTP", otp);
        session.setAttribute("resetEmail", email);
        session.setAttribute("resetUserId", user.getUserId());
        session.setAttribute("otpGeneratedTime", System.currentTimeMillis());

        try {
            boolean emailSent = emailUtil.sendOTP(email, otp, user.getFullName());
            if (emailSent) {
                redirect.addFlashAttribute("successMessage", "Mã OTP đã được gửi đến email của bạn");
                return "redirect:/verify-otp";
            } else {
                redirect.addFlashAttribute("errorMessage", "Không gửi được email. Vui lòng thử lại.");
                return "redirect:/forgot-password";
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirect.addFlashAttribute("errorMessage", "Lỗi khi gửi email.");
            return "redirect:/forgot-password";
        }
    }

    private String generateOTP() {
        SecureRandom random = new SecureRandom();
        int otp = 100_000 + random.nextInt(900_000);
        return String.valueOf(otp);
    }
}
