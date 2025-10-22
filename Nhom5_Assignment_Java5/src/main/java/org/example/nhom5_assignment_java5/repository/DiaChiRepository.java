package org.example.nhom5_assignment_java5.repository;

import org.example.nhom5_assignment_java5.entity.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiaChiRepository extends JpaRepository<DiaChi, String> {

    // ✅ Lấy mã địa chỉ lớn nhất hiện tại (dạng DC01, DC02, ...)
    @Query("SELECT MAX(d.maDC) FROM DiaChi d")
    String findMaxMaDC();
}
