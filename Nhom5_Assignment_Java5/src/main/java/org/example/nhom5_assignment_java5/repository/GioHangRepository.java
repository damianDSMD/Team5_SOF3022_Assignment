package org.example.nhom5_assignment_java5.repository;

import org.example.nhom5_assignment_java5.entity.GioHang;
import org.example.nhom5_assignment_java5.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GioHangRepository extends JpaRepository<GioHang, Integer> {
    List<GioHang> findByKhachHang(KhachHang khachHang);
    GioHang findByKhachHangAndSanPham_MaSP(KhachHang khachHang, String maSP);
}
