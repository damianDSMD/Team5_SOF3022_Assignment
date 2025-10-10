package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "SanPham")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanPham {

    @Id
    @Column(name = "MaSP", length = 50)
    private String maSP;

    @Column(name = "TenSP", nullable = false)
    private String tenSP;

    @Column(name = "DonGia", precision = 15, scale = 2)
    private BigDecimal donGia;

    @Column(name = "SoLuongTonKho")
    private Integer soLuongTonKho;

    @Column(name = "MaLoai", length = 50)
    private String maLoai;

    @OneToMany(mappedBy = "sanPham", fetch = FetchType.EAGER)
    private List<HinhAnh> hinhAnhs;
}