package org.example.nhom5_assignment_java5.service;

import org.example.nhom5_assignment_java5.entity.KhachHang;
import org.example.nhom5_assignment_java5.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KhachHangService {

    @Autowired
    private KhachHangRepository repo;

    public String dangKy(KhachHang kh) {
        if (repo.findByEmail(kh.getEmail()).isPresent()) {
            return "Email đã tồn tại!";
        }
        if (repo.findBySdt(kh.getSdt()).isPresent()) {
            return "Số điện thoại đã tồn tại!";
        }
        repo.save(kh);
        return "Đăng ký thành công!";
    }

    public KhachHang dangNhap(String email, String password) {
        return repo.findByEmailAndPassword(email, password);
    }
}