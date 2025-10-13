package org.example.nhom5_assignment_java5.repository;

import org.example.nhom5_assignment_java5.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    Optional<KhachHang> findByEmail(String email);
    Optional<KhachHang> findBySdt(String sdt);
    KhachHang findByEmailAndPassword(String email, String password);
}
