package org.example.nhom5_assignment_java5.controller;

import org.example.nhom5_assignment_java5.dto.CheckoutForm;
import org.example.nhom5_assignment_java5.entity.*;
import org.example.nhom5_assignment_java5.repository.DiaChiRepository;
import org.example.nhom5_assignment_java5.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private DiaChiRepository diaChiRepository;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private HoaDonService hoaDonService;

    // ✅ GET: Hiển thị trang thanh toán
    @GetMapping
    public String showCheckoutPage(Model model) {
        KhachHang kh = khachHangService.getCurrentKhachHang();
        if (kh == null) {
            return "redirect:/login";
        }

        List<DiaChi> diaChiList = diaChiRepository.findAll()
                .stream()
                .filter(d -> d.getKhachHang() != null && d.getKhachHang().getMaKH().equals(kh.getMaKH()))
                .toList();

        List<GioHang> gioHangList = gioHangService.getGioHangByKhachHang(kh);
        double tongTien = gioHangService.getTongTien(kh);

        model.addAttribute("cart", gioHangList);
        model.addAttribute("tongTien", tongTien);
        model.addAttribute("diachis", diaChiList);
        model.addAttribute("checkoutForm", new CheckoutForm());

        return "checkout";
    }

    // ✅ POST: Xác nhận thanh toán
    @PostMapping("/confirm")
    public String processCheckout(@ModelAttribute("checkoutForm") CheckoutForm form, Model model) {
        KhachHang kh = khachHangService.getCurrentKhachHang();
        if (kh == null) {
            return "redirect:/login";
        }

        // --- Kiểm tra địa chỉ ---
        if (form.getDiaChiId() == null
                && (form.getDiaChiText() == null || form.getDiaChiText().trim().isEmpty())) {

            model.addAttribute("error", "Vui lòng chọn hoặc nhập địa chỉ giao hàng!");
            model.addAttribute("diachis", diaChiRepository.findAll());
            model.addAttribute("checkoutForm", form);
            model.addAttribute("cart", gioHangService.getGioHangByKhachHang(kh));
            return "checkout";
        }

        DiaChi diaChiGiaoHang = null;
        if (form.getDiaChiText() != null && !form.getDiaChiText().isEmpty()) {
            diaChiGiaoHang = new DiaChi();
            diaChiGiaoHang.setDiaChi(form.getDiaChiText());
            diaChiGiaoHang.setKhachHang(kh);
            diaChiGiaoHang.setMacDinh(false);
            diaChiRepository.save(diaChiGiaoHang);
        } else if (form.getDiaChiId() != null) {
            diaChiGiaoHang = diaChiRepository.findById(form.getDiaChiId()).orElse(null);
        }



        if (diaChiGiaoHang == null) {
            model.addAttribute("error", "Địa chỉ giao hàng không hợp lệ!");
            return "checkout";
        }

        // ✅ Tạo hóa đơn thực tế
        List<GioHang> gioHangList = gioHangService.getGioHangByKhachHang(kh);
        double tongTien = gioHangService.getTongTien(kh);

        HoaDon hoaDon = hoaDonService.taoHoaDon(kh, gioHangList, diaChiGiaoHang, tongTien, form.getGhiChu());

        // ✅ Xóa giỏ hàng sau khi đặt hàng
        gioHangService.clearCart(kh);

        // ✅ Chuyển sang trang thành công
        model.addAttribute("hoaDon", hoaDon);
        return "checkout-success";
    }

    // ✅ GET: Trang cảm ơn
    @GetMapping("/success")
    public String showThankYouPage(Model model) {
        model.addAttribute("message", "Đặt hàng thành công!");
        return "checkout-success";
    }
}
