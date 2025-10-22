package org.example.nhom5_assignment_java5.controller;

import org.example.nhom5_assignment_java5.entity.DiaChi;
import org.example.nhom5_assignment_java5.entity.KhachHang;
import org.example.nhom5_assignment_java5.repository.DiaChiRepository;
import org.example.nhom5_assignment_java5.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/diachi")
public class DiaChiController {

    @Autowired
    private DiaChiRepository diaChiRepository;

    @Autowired
    private KhachHangService khachHangService;

    // ✅ 1. Hiển thị danh sách địa chỉ của khách hàng hiện tại
    @GetMapping
    public String listAddresses(Model model) {
        KhachHang kh = khachHangService.getCurrentKhachHang();
        if (kh == null) {
            return "redirect:/login";
        }

        List<DiaChi> diaChiList = diaChiRepository.findAll()
                .stream()
                .filter(d -> d.getKhachHang() != null && d.getKhachHang().getMaKH().equals(kh.getMaKH()))
                .toList();

        model.addAttribute("diachis", diaChiList);
        model.addAttribute("newDiaChi", new DiaChi());
        return "diachi-list"; // 👉 file templates/diachi-list.html
    }

    // ✅ 2. Hiển thị form thêm địa chỉ mới
    @GetMapping("/them")
    public String showAddForm(Model model) {
        model.addAttribute("diaChi", new DiaChi());
        return "diachi-form"; // 👉 file templates/diachi-form.html
    }

    // ✅ 3. Xử lý lưu địa chỉ mới
    @PostMapping("/save")
    public String saveAddress(@ModelAttribute("diaChi") DiaChi diaChi) {
        KhachHang kh = khachHangService.getCurrentKhachHang();
        if (kh == null) {
            return "redirect:/login";
        }
        diaChi.setKhachHang(kh);
        diaChi.setMacDinh(false);
        diaChiRepository.save(diaChi);

        return "redirect:/diachi"; // quay lại danh sách địa chỉ
    }

    // ✅ 4. Xóa địa chỉ
    @GetMapping("/xoa/{id}")
    public String deleteAddress(@PathVariable("id") String id) {
        diaChiRepository.deleteById(id);
        return "redirect:/diachi";
    }

    // ✅ 5. Đặt địa chỉ làm mặc định
    @GetMapping("/macdinh/{id}")
    public String setDefault(@PathVariable("id") Integer id) {
        KhachHang kh = khachHangService.getCurrentKhachHang();
        if (kh == null) {
            return "redirect:/login";
        }

        List<DiaChi> list = diaChiRepository.findAll()
                .stream()
                .filter(d -> d.getKhachHang() != null && d.getKhachHang().getMaKH().equals(kh.getMaKH()))
                .toList();

        for (DiaChi d : list) {
            d.setMacDinh(d.getMaDC().equals(id));

            diaChiRepository.save(d);
        }

        return "redirect:/diachi";
    }
}
