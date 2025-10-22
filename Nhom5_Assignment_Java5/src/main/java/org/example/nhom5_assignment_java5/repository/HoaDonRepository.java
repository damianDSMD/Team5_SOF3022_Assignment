package org.example.nhom5_assignment_java5.repository;

import org.example.nhom5_assignment_java5.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    List<HoaDon> findTop10ByOrderByNgayTaoHDDesc();
    List<HoaDon> findByTrangThai(String trangThai);
}
