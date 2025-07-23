package org.clotheswarehouse_hsf.controller.admin;

import org.clotheswarehouse_hsf.model.User;
import org.clotheswarehouse_hsf.service.UserService;
import org.clotheswarehouse_hsf.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
@RequestMapping("/admin/manageUser")
public class ManageUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String listUsers(@RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "sort", required = false) String sort,
                            Model model) {
        List<User> users = userService.filterUsers(keyword, sort);

        model.addAttribute("users", users);
        model.addAttribute("roles", roleService.getAll());
        model.addAttribute("newUser", new User());
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort);

        return "admin/manageUser/manageUser";
    }

    @GetMapping("/view")
    public String viewOrEditUser(@RequestParam("id") Integer id,
                                 @RequestParam("action") String action,
                                 Model model) {
        User user = userService.findById(id);
        if (user == null) return "redirect:/admin/manageUser";

        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleService.getAll());
        model.addAttribute("newUser", new User());

        if ("edit".equals(action)) {
            model.addAttribute("editUser", user);
            model.addAttribute("activeModal", "edit");
        }

        return "admin/manageUser/manageUser";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute User user,
                             @RequestParam("password") String password,
                             RedirectAttributes ra) throws NoSuchAlgorithmException {

        if (userService.existsByUsername(user.getUsername())) {
            ra.addFlashAttribute("error", "Tên tài khoản đã tồn tại.");
            return "redirect:/admin/manageUser";
        }

        if (userService.existsByEmail(user.getEmail())) {
            ra.addFlashAttribute("error", "Email đã tồn tại.");
            return "redirect:/admin/manageUser";
        }

        userService.addUser(user, password);
        ra.addFlashAttribute("message", "Thêm người dùng thành công!");
        return "redirect:/admin/manageUser";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("editUser") User user,
                             @RequestParam(value = "resetPassword", required = false) boolean resetPassword,
                             RedirectAttributes ra) throws NoSuchAlgorithmException {
        userService.updateUser(user, resetPassword);
        ra.addFlashAttribute("message", "Cập nhật người dùng thành công!");
        return "redirect:/admin/manageUser";
    }

    @PostMapping("/inactive")
    public String inactiveUser(@RequestParam("userId") Integer id,
                               RedirectAttributes ra) {
        userService.inactiveUser(id);
        ra.addFlashAttribute("message", "Đã vô hiệu hoá tài khoản!");
        return "redirect:/admin/manageUser";
    }

}
