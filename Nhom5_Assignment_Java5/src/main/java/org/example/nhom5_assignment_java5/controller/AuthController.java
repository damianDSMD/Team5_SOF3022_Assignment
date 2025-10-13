package org.example.nhom5_assignment_java5.controller;

import jakarta.servlet.http.HttpSession;
import org.example.nhom5_assignment_java5.entity.KhachHang;
import org.example.nhom5_assignment_java5.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private KhachHangService service;

    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("khachHang", new KhachHang());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("khachHang") KhachHang kh, Model model) {
        String result = service.dangKy(kh);
        model.addAttribute("message", result);
        if (result.equals("Đăng ký thành công!")) {
            model.addAttribute("success", true);
        }
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Trả về trang login.html
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {

        KhachHang kh = service.dangNhap(email, password);

        if (kh != null) {
            session.setAttribute("khachHang", kh);
            return "redirect:/home"; // ✅ chuyển sang trang index/home
        } else {
            model.addAttribute("error", "Sai email hoặc mật khẩu!");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
