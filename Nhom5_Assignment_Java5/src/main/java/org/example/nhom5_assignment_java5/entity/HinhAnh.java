package org.example.nhom5_assignment_java5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "HinhAnh")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HinhAnh {

    @Id
    @Column(name = "MaHA", length = 50)
    private String maHA;

    @ManyToOne
    @JoinColumn(name = "MaSP")
    @JsonIgnore
    private SanPham sanPham;

    @Column(name = "TenHA", length = 255)
    private String tenHA;

    @Column(name = "FilePath", length = 500)
    private String filePath;
}