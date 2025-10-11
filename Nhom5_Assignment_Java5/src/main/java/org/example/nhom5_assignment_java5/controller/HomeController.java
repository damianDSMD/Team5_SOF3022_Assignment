package org.example.nhom5_assignment_java5.controller;

import org.example.nhom5_assignment_java5.entity.SanPham;
import org.example.nhom5_assignment_java5.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        // Lấy tất cả sản phẩm từ database
        List<SanPham> danhSachSanPham = sanPhamService.getAllSanPham();

        // Truyền dữ liệu sang view
        model.addAttribute("products", danhSachSanPham);

        // Trả về trang home.html
        return "home";
    }
}