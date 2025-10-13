package org.example.nhom5_assignment_java5.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "KhachHang")
@Getter
@Setter
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKH")
    private Integer maKH;

    @Column(name = "TenKH", nullable = false)
    private String tenKH;

    @Column(name = "SDT", nullable = false, unique = true)
    private String sdt;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "HangTV")
    private String hangTV = "Thường"; // mặc định
}
