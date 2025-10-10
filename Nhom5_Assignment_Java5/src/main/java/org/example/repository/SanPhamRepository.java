package org.example.repository;

import org.example.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, String> {


    List<SanPham> findAll();

    // Tìm sản phẩm theo loại
    List<SanPham> findByMaLoai(String maLoai);

    // Tìm sản phẩm có số lượng tồn kho > 0
    List<SanPham> findBySoLuongTonKhoGreaterThan(Integer soLuong);
}