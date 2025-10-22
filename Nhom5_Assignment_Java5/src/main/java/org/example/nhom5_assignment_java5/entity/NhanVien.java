package org.example.nhom5_assignment_java5.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "NhanVien")
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNV")
    private String maNV;

    @Column(name = "TenNV")
    private String tenNV;

    @Column(name = "ChucVu")
    private String chucVu;

    @Column(name = "Email")
    private String email;

    @Column(name = "Password")
    private String password;

    @Column(name = "SDT")
    private String sdt;
}
