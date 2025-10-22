package org.example.nhom5_assignment_java5.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "NhanVien")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "MaNV", unique = true, length = 50)
    private String maNV;

    @Column(name = "TenNV", nullable = false, length = 255)
    private String tenNV;

    @Column(name = "SDT", length = 20)
    private String sdt;

    @Column(name = "Email", length = 255)
    private String email;

    @Column(name = "ChucVu", length = 100)
    private String chucVu;

    @Column(name = "Password", length = 255)
    private String password;
}
