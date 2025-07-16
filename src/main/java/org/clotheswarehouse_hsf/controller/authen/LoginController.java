package org.clotheswarehouse_hsf.controller.authen;

import jakarta.servlet.http.HttpSession;
import org.clotheswarehouse_hsf.model.ActivityLog;
import org.clotheswarehouse_hsf.model.User;
import org.clotheswarehouse_hsf.service.ActivityLogService;
import org.clotheswarehouse_hsf.service.UserService;
import org.clotheswarehouse_hsf.utils.PasswordUtil;
import org.clotheswarehouse_hsf.utils.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;
    private final PasswordUtil passwordUtil;
    private final ActivityLogService activityLogService;

    public LoginController(UserService userService, PasswordUtil passwordUtil, ActivityLogService activityLogService) {
        this.userService = userService;
        this.passwordUtil = passwordUtil;
        this.activityLogService = activityLogService;
    }


    @GetMapping
    public String showLoginForm(Model model, HttpSession session) {

        String successMessage = SessionUtil.getAndRemoveSuccessMessage(session);
        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
        }

        String errorMessage = SessionUtil.getAndRemoveErrorMessage(session);
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
        }

        return "authen/login";
    }

    @PostMapping
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               Model model,
                               HttpSession session) {

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            model.addAttribute("errorMessage", "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu");
            return "authen/login";
        }

        User user = userService.findByUsername(username);
        if (user == null) {
            model.addAttribute("errorMessage", "Tên đăng nhập không tồn tại");
            return "authen/login";
        }

        if (!passwordUtil.verifyPassword(password, user.getPasswordHash())) {
            model.addAttribute("errorMessage", "Mật khẩu không chính xác");
            return "authen/login";
        }

        if (!user.isActive()) {
            model.addAttribute("errorMessage", "Tài khoản đã bị vô hiệu hóa");
            return "authen/login";
        }

        activityLogService.logActivity(
                user,
                ActivityLog.ActionType.LOGIN,
                null,
                null,
                "Đăng nhập hệ thống"
        );
        SessionUtil.setUser(session, user);
        SessionUtil.setSuccessMessage(session, "Đăng nhập thành công! Chào " + user.getFullName());
        user.setLastLogin(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));
        userService.update(user);
        String roleId = user.getRole().getRoleId();
        if ("admin".equalsIgnoreCase(roleId)) {
            return "redirect:/profile";
        } else {
            return "redirect:/profile";
        }
    }
}
