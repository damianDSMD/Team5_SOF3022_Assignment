package org.example.nhom5_assignment_java5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "GioHang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GioHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaGH")
    private Integer maGH;

    @ManyToOne
    @JoinColumn(name = "MaKH", referencedColumnName = "MaKH")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "MaSP", referencedColumnName = "MaSP")
    private SanPham sanPham;

    @Column(name = "SoLuong")
    private Integer soLuong;

}
