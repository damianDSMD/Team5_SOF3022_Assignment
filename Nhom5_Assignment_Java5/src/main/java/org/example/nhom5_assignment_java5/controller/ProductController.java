package org.example.nhom5_assignment_java5.controller;

import org.example.nhom5_assignment_java5.entity.SanPham;
import org.example.nhom5_assignment_java5.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {

    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable("id") String maSP, Model model) {
        System.out.println("=================================");
        System.out.println("ProductController: Viewing product detail for ID: " + maSP);

        try {
            // Lấy sản phẩm theo mã
            SanPham sanPham = sanPhamService.getSanPhamById(maSP);

            if (sanPham != null) {
                System.out.println("Product found: " + sanPham.getTenSP());
                System.out.println("- Price: " + sanPham.getDonGia());
                System.out.println("- Stock: " + sanPham.getSoLuongTonKho());
                System.out.println("- Images: " + (sanPham.getHinhAnhs() != null ? sanPham.getHinhAnhs().size() : 0));

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

        System.out.println("Returning to product-detail.html template");
        System.out.println("=================================");

        return "product-detail";
    }
}