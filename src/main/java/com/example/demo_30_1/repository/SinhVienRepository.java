package com.example.demo_30_1.repository;

import com.example.demo_30_1.model.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, String> {
    // Custom query to find max ID if needed, or we can just fetch all and calculate in code for simplicity as per requirement
    // "Làm ID tự tăng bằng với maxId +1 khi tạo... ID lưu trong CSDL là chuỗi"
    
    @Query("SELECT s.id FROM SinhVien s")
    List<String> findAllIds();
}
