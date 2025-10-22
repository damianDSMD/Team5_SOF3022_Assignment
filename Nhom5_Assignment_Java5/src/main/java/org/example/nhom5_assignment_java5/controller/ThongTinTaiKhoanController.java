package org.example.nhom5_assignment_java5.controller;


import jakarta.servlet.http.HttpSession;
import org.example.nhom5_assignment_java5.entity.KhachHang;
import org.example.nhom5_assignment_java5.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ThongTinTaiKhoanController {

    @Autowired
    private KhachHangRepository khachHangRepository;

    // ✅ Trang hiển thị thông tin cá nhân
    @GetMapping
    public String viewProfile(HttpSession session, Model model) {
        KhachHang kh = (KhachHang) session.getAttribute("khachHang");
        if (kh == null) {
            return "redirect:/login";
        }
        model.addAttribute("khachHang", kh);
        return "profile";
    }

    // ✅ Cập nhật thông tin cá nhân
    @PostMapping("/update")
    public String updateProfile(
            @ModelAttribute("khachHang") KhachHang form,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        KhachHang current = (KhachHang) session.getAttribute("khachHang");
        if (current == null) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập lại!");
            return "redirect:/login";
        }

        Optional<KhachHang> optionalKh = khachHangRepository.findById(current.getMaKH());
        if (optionalKh.isPresent()) {
            KhachHang kh = optionalKh.get();
            kh.setTenKH(form.getTenKH());
            kh.setEmail(form.getEmail());
            kh.setSdt(form.getSdt());
            khachHangRepository.save(kh);
            session.setAttribute("khachHang", kh);
            redirectAttributes.addFlashAttribute("success", "✅ Cập nhật thông tin thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "❌ Không tìm thấy thông tin khách hàng!");
        }

        return "redirect:/profile";
    }

    // ✅ Đổi mật khẩu
    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        KhachHang kh = (KhachHang) session.getAttribute("khachHang");
        if (kh == null) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập lại!");
            return "redirect:/login";
        }

        // ⚠️ Kiểm tra mật khẩu cũ
        if (!kh.getPassword().equals(oldPassword)) {
            redirectAttributes.addFlashAttribute("errorPass", "❌ Mật khẩu cũ không chính xác!");
            return "redirect:/profile";
        }

        // ⚠️ Kiểm tra xác nhận mật khẩu mới
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorPass", "❌ Mật khẩu xác nhận không khớp!");
            return "redirect:/profile";
        }

        // ✅ Cập nhật mật khẩu
        kh.setPassword(newPassword);
        khachHangRepository.save(kh);
        session.setAttribute("khachHang", kh);

        redirectAttributes.addFlashAttribute("successPass", "✅ Đổi mật khẩu thành công!");
        return "redirect:/profile";
    }

}
