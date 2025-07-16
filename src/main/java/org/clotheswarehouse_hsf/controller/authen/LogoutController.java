package org.clotheswarehouse_hsf.controller.authen;

import org.clotheswarehouse_hsf.model.ActivityLog;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpSession;
import org.clotheswarehouse_hsf.model.User;
import org.clotheswarehouse_hsf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.clotheswarehouse_hsf.service.ActivityLogService;

@Controller
@RequestMapping("/logout")
public class LogoutController {
    @Autowired
    private ActivityLogService activityLogService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String handleLogout(@RequestParam(name = "action", required = false, defaultValue = "logout") String action,
                               HttpSession session,
                               RedirectAttributes redirect) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            redirect.addFlashAttribute("errorMessage", "Bạn chưa đăng nhập");
            return "redirect:/login";
        }

        if ("confirm".equals(action)) {
            return "redirect:/login";
        } else {

            session.invalidate();
            redirect.addFlashAttribute("successMessage", "Đăng xuất thành công");
            return "redirect:/login";
        }
    }


    @PostMapping
    public String postLogout(@RequestParam(name = "action", required = false, defaultValue = "logout") String action,
                             HttpSession session,
                             RedirectAttributes redirect) {

        if ("logout".equals(action)) {
            return doLogout(session, redirect);
        } else {
            redirect.addFlashAttribute("errorMessage", "Hành động không hợp lệ");
            return "redirect:/dashboard";
        }
    }

    private String doLogout(HttpSession session, RedirectAttributes redirect) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            redirect.addFlashAttribute("errorMessage", "Bạn chưa đăng nhập");
            return "redirect:/login";
        }
        activityLogService.logActivity(
                currentUser,
                ActivityLog.ActionType.LOGOUT,
                null,
                null,
                "Đăng xuất khỏi hệ thống"
        );
        session.invalidate();
        redirect.addFlashAttribute("successMessage", "Đăng xuất thành công");
        return "redirect:/login";
    }


    private String showLogoutConfirmation(HttpSession session, RedirectAttributes redirect) {
        if (session.getAttribute("currentUser") == null) {
            redirect.addFlashAttribute("errorMessage", "Bạn chưa đăng nhập");
            return "redirect:/login";
        }

        return "authen/logout-confirm";
    }
}
