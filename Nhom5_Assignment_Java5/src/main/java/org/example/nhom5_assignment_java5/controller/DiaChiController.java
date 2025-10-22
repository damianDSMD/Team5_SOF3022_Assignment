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
        return "diachi-list";
    }

    @GetMapping("/them")
    public String showAddForm(Model model) {
        model.addAttribute("diaChi", new DiaChi());
        return "diachi-form";
    }

    @PostMapping("/save")
    public String saveAddress(@ModelAttribute("diaChi") DiaChi diaChi) {
        KhachHang kh = khachHangService.getCurrentKhachHang();
        if (kh == null) {
            return "redirect:/login";
        }
        diaChi.setKhachHang(kh);
        diaChi.setMacDinh(false);
        diaChiRepository.save(diaChi);
        return "redirect:/diachi";
    }

    @GetMapping("/xoa/{id}")
    public String deleteAddress(@PathVariable("id") Integer id) {
        diaChiRepository.deleteById(id);
        return "redirect:/diachi";
    }

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
