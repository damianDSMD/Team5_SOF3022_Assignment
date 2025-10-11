package org.example.nhom5_assignment_java5.service;

import org.example.nhom5_assignment_java5.entity.SanPham;
import org.example.nhom5_assignment_java5.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    // Lấy tất cả sản phẩm
    public List<SanPham> getAllSanPham() {
        return sanPhamRepository.findAll();
    }

    // Lấy sản phẩm có tồn kho > 0
    public List<SanPham> getSanPhamConHang() {
        return sanPhamRepository.findBySoLuongTonKhoGreaterThan(0);
    }

    // Lấy sản phẩm theo loại
    public List<SanPham> getSanPhamByLoai(String maLoai) {
        return sanPhamRepository.findByMaLoai(maLoai);
    }

    // Lấy sản phẩm theo mã
    public SanPham getSanPhamById(String maSP) {
        return sanPhamRepository.findById(maSP).orElse(null);
    }
}