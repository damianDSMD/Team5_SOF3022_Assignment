package org.example.nhom5_assignment_java5.controller;

import jakarta.servlet.http.HttpSession;
import org.example.nhom5_assignment_java5.entity.SanPham;
import org.example.nhom5_assignment_java5.entity.KhachHang;
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
    public String home(Model model, HttpSession session) {
        List<SanPham> danhSachSanPham = sanPhamService.getAllSanPham();

        // 🔥 Limit how many products to show (e.g., 8)
        int maxProducts = 8;
        if (danhSachSanPham.size() > maxProducts) {
            danhSachSanPham = danhSachSanPham.subList(0, maxProducts);
        }

        model.addAttribute("products", danhSachSanPham);

        // Giữ thông tin người dùng nếu đã đăng nhập
        KhachHang khachHang = (KhachHang) session.getAttribute("khachHang");
        if (khachHang != null) {
            model.addAttribute("loggedIn", true);
            model.addAttribute("username", khachHang.getTenKH());
        } else {
            model.addAttribute("loggedIn", false);
        }

        return "home";
    }
}
