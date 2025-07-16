package org.clotheswarehouse_hsf.controller.manager;

import org.clotheswarehouse_hsf.model.Supplier;
import org.clotheswarehouse_hsf.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Controller
@RequestMapping("/manager/manage-supplier")
public class ManageSupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public String listSuppliers(@RequestParam(value = "search", required = false) String search,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                Model model) {

        int pageSize = 8;
        List<Supplier> suppliers = supplierService.findSuppliers(search, page, pageSize);
        int total = supplierService.getTotalFilteredSuppliers(search);
        int totalPages = (int) Math.ceil((double) total / pageSize);

        model.addAttribute("newSupplier", new Supplier());
        model.addAttribute("editSupplier", new Supplier());
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalSuppliers", total);
        model.addAttribute("searchTerm", search);

        return "manager/suppliers/supplier";
    }


    @PostMapping("/create")
    public String createSupplier(@ModelAttribute Supplier supplier,
                                 RedirectAttributes redirectAttributes,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "") String search) {

        StringBuilder errors = validateSupplier(supplier, true);
        if (errors.length() > 0) {
            redirectAttributes.addFlashAttribute("error", errors.toString());
            return "redirect:/manager/manage-supplier?page=" + page + "&search=" + search;
        }

        supplierService.save(supplier);
        redirectAttributes.addFlashAttribute("message", "Thêm nhà cung cấp thành công!");
        return "redirect:/manager/manage-supplier?page=" + page + "&search=" + search;
    }

    @PostMapping("/edit")
    public String updateSupplier(@ModelAttribute Supplier supplier,
                                 RedirectAttributes redirectAttributes,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "") String search) {

        StringBuilder errors = validateSupplier(supplier, false);
        if (errors.length() > 0) {
            redirectAttributes.addFlashAttribute("error", errors.toString());
            return "redirect:/manager/manage-supplier?page=" + page + "&search=" + search;
        }

        supplierService.save(supplier);
        redirectAttributes.addFlashAttribute("message", "Cập nhật nhà cung cấp thành công!");
        return "redirect:/manager/manage-supplier?page=" + page + "&search=" + search;
    }

    @GetMapping("/delete")
    public String deleteSupplier(@RequestParam("id") int id,
                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                 @RequestParam(value = "search", defaultValue = "") String search,
                                 RedirectAttributes redirectAttributes) {

        boolean success = supplierService.delete(id);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Xóa thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy nhà cung cấp.");
        }

        return "redirect:/manager/manage-supplier?page=" + page + "&search=" + search;
    }

    private StringBuilder validateSupplier(Supplier supplier, boolean isCreate) {
        StringBuilder error = new StringBuilder();

        if (supplier.getSupplierName() == null || supplier.getSupplierName().trim().isEmpty())
            error.append("Tên nhà cung cấp không được để trống. ");
        else if (supplier.getSupplierName().length() > 100)
            error.append("Tên nhà cung cấp không được quá 100 ký tự. ");
        else if (isCreate && supplierService.isSupplierNameExist(supplier.getSupplierName()))
            error.append("Tên nhà cung cấp đã tồn tại. ");

        if (supplier.getContactPerson() == null || supplier.getContactPerson().trim().isEmpty())
            error.append("Tên người liên hệ không được để trống. ");
        else if (supplier.getContactPerson().length() > 100)
            error.append("Tên người liên hệ không được quá 100 ký tự. ");

        if (supplier.getPhoneNumber() == null || !supplier.getPhoneNumber().matches("^\\d{10,11}$"))
            error.append("Số điện thoại phải có 10-11 chữ số. ");

        if (supplier.getEmail() == null || !supplier.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
            error.append("Email không đúng định dạng. ");
        else if (isCreate && supplierService.isEmailExist(supplier.getEmail()))
            error.append("Email đã tồn tại. ");

        if (supplier.getAddress() == null || supplier.getAddress().length() > 255)
            error.append("Địa chỉ không được quá 255 ký tự. ");

        return error;
    }
}

