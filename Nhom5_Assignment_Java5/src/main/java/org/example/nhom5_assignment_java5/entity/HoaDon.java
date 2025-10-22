package org.example.nhom5_assignment_java5.entity;

import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "HoaDon")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String maHD; // optional display code

    @ManyToOne
    @JoinColumn(name = "KhachHangId")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "NhanVienId")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "DiaChiId")
    private DiaChi diaChi;

    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
    private Double tongTien;
    private String trangThai; // Chờ xác nhận, Đã xác nhận, Đang giao, Hoàn thành, Đã hủy
    private String ghiChu;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoaDonChiTiet> chiTiet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public DiaChi getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(DiaChi diaChi) {
        this.diaChi = diaChi;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public LocalDateTime getNgayCapNhat() {
        return ngayCapNhat;
    }

    public void setNgayCapNhat(LocalDateTime ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }

    public Double getTongTien() {
        return tongTien;
    }

    public void setTongTien(Double tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public List<HoaDonChiTiet> getChiTiet() {
        return chiTiet;
    }

    public void setChiTiet(List<HoaDonChiTiet> chiTiet) {
        this.chiTiet = chiTiet;
    }
}