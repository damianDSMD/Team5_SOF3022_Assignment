package org.example.nhom5_assignment_java5.controller;

import jakarta.servlet.http.HttpSession;
import org.example.nhom5_assignment_java5.entity.KhachHang;
import org.example.nhom5_assignment_java5.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private KhachHangService service;

    // ✅ Hiển thị form đăng ký
    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("khachHang", new KhachHang());
        return "register";
    }

    // ✅ Xử lý đăng ký người dùng
    @PostMapping("/register")
    public String register(
            @ModelAttribute("khachHang") KhachHang kh,
            @RequestParam("confirm") String confirm,
            Model model
    ) {
        // Kiểm tra xác nhận mật khẩu
        if (!kh.getPassword().equals(confirm)) {
            model.addAttribute("error", "Mật khẩu xác nhận không khớp!");
            return "register";
        }

        // Gán mặc định hạng thành viên là "browze"
        kh.setHangTV("browze");

        // Gọi service đăng ký
        String result = service.dangKy(kh);
        if (!result.equals("Đăng ký thành công!")) {
            model.addAttribute("error", result);
            return "register";
        }

        // Nếu thành công → hiển thị thông báo và chuyển hướng đến login
        model.addAttribute("message", "Đăng ký thành công! Hãy đăng nhập.");
        return "login";
    }

    // ✅ Hiển thị form đăng nhập
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // ✅ Xử lý đăng nhập
    @PostMapping("/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session,
            Model model
    ) {
        KhachHang kh = service.dangNhap(email, password);

        if (kh != null) {
            session.setAttribute("khachHang", kh);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Sai email hoặc mật khẩu!");
            return "login";
        }
    }

    // ✅ Đăng xuất
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
