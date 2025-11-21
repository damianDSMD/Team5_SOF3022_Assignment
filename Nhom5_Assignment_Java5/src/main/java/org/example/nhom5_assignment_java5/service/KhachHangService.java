package org.example.nhom5_assignment_java5.service;

import jakarta.servlet.http.HttpSession;
import org.example.nhom5_assignment_java5.entity.KhachHang;
import org.example.nhom5_assignment_java5.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder; // ← ĐỔI IMPORT
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KhachHangService implements UserDetailsService {

    @Autowired
    private KhachHangRepository repo;

    @Autowired
    private HttpSession session;

    @Autowired // ← INJECT BEAN
    private PasswordEncoder passwordEncoder; // ← ĐỔI THÀNH PasswordEncoder

    // ===== REGISTER =====
    public String dangKy(KhachHang kh) {

        if (repo.findByEmail(kh.getEmail()).isPresent()) {
            return "Email đã tồn tại!";
        }
        if (repo.findBySdt(kh.getSdt()).isPresent()) {
            return "Số điện thoại đã tồn tại!";
        }

        // Sử dụng bean đã inject
        kh.setPassword(passwordEncoder.encode(kh.getPassword()));

        repo.save(kh);
        return "Đăng ký thành công!";
    }

    // ===== SPRING SECURITY LOGIN =====
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        KhachHang kh = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng: " + email));

        String role = "ROLE_" + (kh.getHangTV() == null ? "USER" : kh.getHangTV().toUpperCase());

        return User.builder()
                .username(kh.getEmail())
                .password(kh.getPassword()) // already encoded
                .authorities(new SimpleGrantedAuthority(role))
                .build();
    }

    public KhachHang getCurrentKhachHang() {
        return (KhachHang) session.getAttribute("khachHang");
    }
}