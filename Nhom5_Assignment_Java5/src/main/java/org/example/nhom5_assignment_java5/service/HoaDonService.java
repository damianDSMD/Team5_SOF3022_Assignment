package org.example.nhom5_assignment_java5.service;

import org.example.nhom5_assignment_java5.entity.*;
import org.example.nhom5_assignment_java5.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    public HoaDon taoHoaDon(KhachHang kh, List<GioHang> gioHangList, DiaChi diaChi, double tongTien, String ghiChu) {
        HoaDon hoaDon = new HoaDon();

        hoaDon.setKhachHang(kh);
        hoaDon.setNhanVien(null); // optional
        hoaDon.setDiaChi(diaChi);
        hoaDon.setNgayTaoHD(new Date());
        hoaDon.setNgayUpHD(new Date());
        hoaDon.setTrangThai("Chờ xác nhận");
        hoaDon.setGhiChu(ghiChu);
        hoaDon.setTongTien(BigDecimal.valueOf(tongTien));

        return hoaDonRepository.save(hoaDon);
    }
}
