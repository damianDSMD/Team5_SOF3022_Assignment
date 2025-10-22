package org.example.nhom5_assignment_java5.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "HoaDon")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHD")
    private Integer maHD;

    @ManyToOne
    @JoinColumn(name = "MaKH")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "MaNV", referencedColumnName = "MaNV")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "MaDC")
    private DiaChi diaChi;

    @Temporal(TemporalType.DATE)
    @Column(name = "NgayTaoHD")
    private Date ngayTaoHD;

    @Temporal(TemporalType.DATE)
    @Column(name = "NgayUpHD")
    private Date ngayUpHD;

    @Column(name = "MaGG")
    private String maGG;

    @Column(name = "TongTien", precision = 15, scale = 2)
    private BigDecimal tongTien;

    @Column(name = "TrangThai")
    private String trangThai;

    @Column(name = "GhiChu")
    private String ghiChu;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoaDonChiTiet> chiTiet;

    // ---- Getters & Setters ----
    public Integer getMaHD() { return maHD; }
    public void setMaHD(Integer maHD) { this.maHD = maHD; }

    public KhachHang getKhachHang() { return khachHang; }
    public void setKhachHang(KhachHang khachHang) { this.khachHang = khachHang; }

    public NhanVien getNhanVien() { return nhanVien; }
    public void setNhanVien(NhanVien nhanVien) { this.nhanVien = nhanVien; }

    public DiaChi getDiaChi() { return diaChi; }
    public void setDiaChi(DiaChi diaChi) { this.diaChi = diaChi; }

    public Date getNgayTaoHD() { return ngayTaoHD; }
    public void setNgayTaoHD(Date ngayTaoHD) { this.ngayTaoHD = ngayTaoHD; }

    public Date getNgayUpHD() { return ngayUpHD; }
    public void setNgayUpHD(Date ngayUpHD) { this.ngayUpHD = ngayUpHD; }

    public String getMaGG() { return maGG; }
    public void setMaGG(String maGG) { this.maGG = maGG; }

    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    public List<HoaDonChiTiet> getChiTiet() { return chiTiet; }
    public void setChiTiet(List<HoaDonChiTiet> chiTiet) { this.chiTiet = chiTiet; }
}
