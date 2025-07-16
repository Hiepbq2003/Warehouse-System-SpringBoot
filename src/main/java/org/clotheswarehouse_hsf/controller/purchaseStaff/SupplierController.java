package org.clotheswarehouse_hsf.controller.purchaseStaff;


import org.clotheswarehouse_hsf.model.Supplier;
import org.clotheswarehouse_hsf.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/purchase/suppliers")
    public String viewSuppliers(@RequestParam(value = "search", required = false) String search,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                Model model) {
        int pageSize = 8;

        List<Supplier> suppliers = supplierService.findSuppliers(search, page, pageSize);
        int total = supplierService.getTotalFilteredSuppliers(search);
        int totalPages = (int) Math.ceil((double) total / pageSize);

        model.addAttribute("suppliers", suppliers);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalSuppliers", total);
        model.addAttribute("search", search);

        return "purchaseStaff/supplier/supplierList";
    }



}