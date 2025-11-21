package org.example.nhom5_assignment_java5.controller;

import jakarta.servlet.http.HttpSession;
import org.example.nhom5_assignment_java5.entity.KhachHang;
import org.example.nhom5_assignment_java5.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private KhachHangService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Register page
    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("khachHang", new KhachHang());
        return "register";
    }

    // ✅ Handle register
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

        // Mã hoá mật khẩu bằng BCrypt
        kh.setPassword(passwordEncoder.encode(kh.getPassword()));

        // Gán mặc định hạng thành viên
        kh.setHangTV("browze");

        // Gọi service để lưu
        String result = service.dangKy(kh);

        if (!result.equals("Đăng ký thành công!")) {
            model.addAttribute("error", result);
            return "register";
        }

        model.addAttribute("message", "Đăng ký thành công! Hãy đăng nhập.");
        return "login";
    }

    // ✅ Login page (Spring Security handles authentication)
    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                Model model) {
        if (error != null) {
            model.addAttribute("error", "Email hoặc mật khẩu không đúng!");
        }
        return "login";
    }

    // ❌ Remove POST /login — Spring Security handles it
    // ❌ DO NOT authenticate manually

    // ❗ This is kept so you can manually clear custom session values if needed
    @GetMapping("/logout-success")
    public String logoutSuccess(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}
