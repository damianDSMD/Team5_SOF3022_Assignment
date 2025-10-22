package org.example.nhom5_assignment_java5.repository;

import org.example.nhom5_assignment_java5.entity.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaChiRepository extends JpaRepository<DiaChi, Integer> {
}
