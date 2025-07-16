package org.clotheswarehouse_hsf.controller.profile;

import jakarta.servlet.http.HttpSession;
import org.clotheswarehouse_hsf.model.User;
import org.clotheswarehouse_hsf.service.UserService;
import org.clotheswarehouse_hsf.utils.PasswordUtil;
import org.clotheswarehouse_hsf.utils.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final PasswordUtil passwordUtil;

    public ProfileController(UserService userService, PasswordUtil passwordUtil) {
        this.userService = userService;
        this.passwordUtil = passwordUtil;
    }

    // Trang xem hoặc chỉnh sửa profile (dùng chung view)
    @GetMapping
    public String viewOrEditProfile(@RequestParam(value = "action", required = false) String action,
                                    HttpSession session,
                                    Model model) {
        User currentUser = SessionUtil.getUser(session);
        if (currentUser == null) {
            SessionUtil.setErrorMessage(session, "Vui lòng đăng nhập để xem profile");
            return "redirect:/login";
        }

        String formattedCreatedAt = "";
        if (currentUser.getCreatedAt() != null) {
            formattedCreatedAt = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(currentUser.getCreatedAt());
        }

        model.addAttribute("formattedCreatedAt", formattedCreatedAt);
        model.addAttribute("user", currentUser);
        model.addAttribute("errorMessage", SessionUtil.getAndRemoveErrorMessage(session));
        model.addAttribute("successMessage", SessionUtil.getAndRemoveSuccessMessage(session));

        return "profile/profile"; // dùng chung 1 view
    }

    // Cập nhật thông tin
    @PostMapping(params = "action=updateProfile")
    public String updateProfile(HttpSession session,
                                @RequestParam String fullName,
                                @RequestParam(required = false) String phone) {
        User currentUser = SessionUtil.getUser(session);
        if (currentUser == null) {
            SessionUtil.setErrorMessage(session, "Vui lòng đăng nhập để cập nhật profile");
            return "redirect:/login";
        }

        if (fullName == null || fullName.trim().isEmpty()) {
            SessionUtil.setErrorMessage(session, "Vui lòng nhập họ tên");
            return "redirect:/profile?action=edit";
        }

        try {
            currentUser.setFullName(fullName.trim());
            currentUser.setPhone(phone);
            userService.save(currentUser);

            SessionUtil.setUser(session, currentUser);
            SessionUtil.setSuccessMessage(session, "Cập nhật thông tin thành công");
            return "redirect:/profile?action=edit";
        } catch (Exception e) {
            SessionUtil.setErrorMessage(session, "Có lỗi xảy ra khi cập nhật thông tin");
            return "redirect:/profile?action=edit";
        }
    }

    // Đổi mật khẩu
    @PostMapping(params = "action=changePassword")
    public String changePassword(HttpSession session,
                                 @RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword) {
        User currentUser = SessionUtil.getUser(session);
        if (currentUser == null) {
            SessionUtil.setErrorMessage(session, "Vui lòng đăng nhập để đổi mật khẩu");
            return "redirect:/login";
        }

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            SessionUtil.setErrorMessage(session, "Vui lòng điền đầy đủ thông tin mật khẩu");
            return "redirect:/profile?action=edit";
        }

        if (!newPassword.equals(confirmPassword)) {
            SessionUtil.setErrorMessage(session, "Mật khẩu mới xác nhận không khớp");
            return "redirect:/profile?action=edit";
        }

        if (!passwordUtil.isValidPassword(newPassword)) {
            SessionUtil.setErrorMessage(session, "Mật khẩu mới phải có ít nhất 3 ký tự,  chữ và số");
            return "redirect:/profile?action=edit";
        }

        try {
            if (!passwordUtil.verifyPassword(currentPassword, currentUser.getPasswordHash())) {
                SessionUtil.setErrorMessage(session, "Mật khẩu hiện tại không chính xác");
                return "redirect:/profile?action=edit";
            }

            currentUser.setPasswordHash(passwordUtil.hashPassword(newPassword));
            userService.save(currentUser);

            SessionUtil.setSuccessMessage(session, "Đổi mật khẩu thành công");
            return "redirect:/profile?action=edit";

        } catch (Exception e) {
            SessionUtil.setErrorMessage(session, "Có lỗi xảy ra khi đổi mật khẩu");
            return "redirect:/profile?action=edit";
        }
    }
}
