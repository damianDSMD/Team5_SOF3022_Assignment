package org.example.nhom5_assignment_java5.controller;

import org.example.nhom5_assignment_java5.entity.SanPham;
import org.example.nhom5_assignment_java5.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    @Autowired
    private SanPhamService sanPhamService;

    // =========================
    // 🧩 1️⃣ Product Detail
    // =========================
    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable("id") String maSP, Model model) {
        System.out.println("=================================");
        System.out.println("ProductController: Viewing product detail for ID: " + maSP);

        try {
            SanPham sanPham = sanPhamService.getSanPhamById(maSP);

            if (sanPham != null) {
                System.out.println("Product found: " + sanPham.getTenSP());
                model.addAttribute("product", sanPham);
            } else {
                System.out.println("Product NOT found!");
                model.addAttribute("product", null);
            }

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("product", null);
        }

        return "product-detail";
    }

    // =========================
    // 🛍️ 2️⃣ Product List
    // =========================
    @GetMapping("/product-list")
    public String productList(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "hideOutOfStock", required = false) Boolean hideOutOfStock,
            Model model) {

        System.out.println("=== ProductController: product-list ===");
        System.out.println("Filter: type=" + type + ", sort=" + sort + ", hideOutOfStock=" + hideOutOfStock);

        List<SanPham> products = sanPhamService.getAllSanPham();

        // 🔹 Filter by category
        if (type != null && !type.isEmpty()) {
            products = products.stream()
                    .filter(sp -> type.equalsIgnoreCase(sp.getMaLoai()))
                    .collect(Collectors.toList());
        }

        // 🔹 Hide out of stock
        if (Boolean.TRUE.equals(hideOutOfStock)) {
            products = products.stream()
                    .filter(sp -> sp.getSoLuongTonKho() != null && sp.getSoLuongTonKho() > 0)
                    .collect(Collectors.toList());
        }

        // 🔹 Sort by price
        if ("asc".equalsIgnoreCase(sort)) {
            products.sort(Comparator.comparing(SanPham::getDonGia));
        } else if ("desc".equalsIgnoreCase(sort)) {
            products.sort(Comparator.comparing(SanPham::getDonGia).reversed());
        }

        model.addAttribute("products", products);
        return "product"; // ✅ product.html (your product-list page)
    }
}
