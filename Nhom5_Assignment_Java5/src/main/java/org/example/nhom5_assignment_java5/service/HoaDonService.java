package org.example.nhom5_assignment_java5.service;

import org.example.nhom5_assignment_java5.entity.*;
import org.example.nhom5_assignment_java5.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    public HoaDon taoHoaDon(KhachHang kh, List<GioHang> gioHangList, DiaChi diaChi, Double tongTien, String ghiChu) {
        HoaDon hoaDon = new HoaDon();

        // Sinh mã hóa đơn (ví dụ: HD_20251013_123)
        hoaDon.setMaHD("HD" + System.currentTimeMillis());
        hoaDon.setKhachHang(kh);
        hoaDon.setNhanVien(null); // Tạm thời chưa có nhân viên
        hoaDon.setDiaChi(diaChi);
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setNgayCapNhat(LocalDateTime.now());
        hoaDon.setTrangThai("Chờ xác nhận");
        hoaDon.setGhiChu(ghiChu);
        hoaDon.setTongTien(tongTien);

        // ✅ Lưu hóa đơn vào DB
        return hoaDonRepository.save(hoaDon);
    }
}
