package org.clotheswarehouse_hsf.controller.manager;

import jakarta.servlet.http.HttpSession;
import org.clotheswarehouse_hsf.model.ActivityLog;
import org.clotheswarehouse_hsf.model.Category;
import org.clotheswarehouse_hsf.service.ActivityLogService;
import org.clotheswarehouse_hsf.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.clotheswarehouse_hsf.model.User;

@Controller
@RequestMapping("/manager/manage-category")
public class ManageCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String handleGet(
            @RequestParam(value = "action", defaultValue = "list") String action,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "search", required = false) String search,
            Model model,
            HttpSession session) {

        switch (action) {
            case "create":
                return "manager/category/addCategory";

            case "edit":
                if (id == null) {
                    setMessage(session, "ID không hợp lệ.", "error");
                    return "redirect:/manager/manage-category";
                }

                Category category = categoryService.findById(id);
                if (category == null) {
                    setMessage(session, "Danh mục không tồn tại.", "error");
                    return "redirect:/manager/manage-category";
                }

                model.addAttribute("category", category);
                return "manager/category/editCategory";

            case "delete":
                if (id != null) {
                    if (categoryService.isInUse(id)) {
                        setMessage(session, "Không thể xóa vì đang được sử dụng.", "error");
                    } else {
                        categoryService.delete(id);
                        setMessage(session, "Xóa danh mục thành công!", "success");
                    }
                }
                return "redirect:/manager/manage-category";

            default:
                int size = 10;
                Page<Category> pageData = categoryService.searchCategories(
                        (search != null) ? search : "",
                        PageRequest.of(page - 1, size)
                );

                model.addAttribute("categories", pageData.getContent());
                model.addAttribute("currentPage", page);
                model.addAttribute("totalPages", pageData.getTotalPages());
                model.addAttribute("totalItems", pageData.getTotalElements());
                model.addAttribute("search", search);

                return "manager/category/category";
        }
    }

    @PostMapping
    public String handlePost(
            @RequestParam("action") String action,
            @RequestParam(required = false) Integer id,
            @RequestParam("name") String name,
            HttpSession session) {

        if (!isValidName(name, session)) {
            return "redirect:/manager/manage-category?action=" + action +
                    ((id != null) ? "&id=" + id : "");
        }

        if (action.equals("create")) {
            if (categoryService.existsByName(name)) {
                setMessage(session, "Tên danh mục đã tồn tại.", "error");
                return "redirect:/manager/manage-category?action=create";
            }
            categoryService.create(name);
            setMessage(session, "Tạo danh mục thành công!", "success");
        }

        if (action.equals("edit")) {
            if (id == null) {
                setMessage(session, "ID không hợp lệ.", "error");
                return "redirect:/manager/manage-category";
            }
            if (categoryService.existsByNameExceptId(name, id)) {
                setMessage(session, "Tên danh mục đã tồn tại.", "error");
                return "redirect:/manager/manage-category?action=edit&id=" + id;
            }
            categoryService.update(id, name);
            setMessage(session, "Cập nhật danh mục thành công!", "success");
        }

        return "redirect:/manager/manage-category";
    }

    private boolean isValidName(String name, HttpSession session) {
        if (name == null || name.trim().isEmpty()) {
            setMessage(session, "Tên danh mục không được để trống.", "error");
            return false;
        }
        if (name.length() > 45) {
            setMessage(session, "Tên danh mục không được vượt quá 45 ký tự.", "error");
            return false;
        }
        return true;
    }

    private void setMessage(HttpSession session, String message, String type) {
        session.setAttribute("message", message);
        session.setAttribute("messageType", type); // "success" | "error"
    }


}
