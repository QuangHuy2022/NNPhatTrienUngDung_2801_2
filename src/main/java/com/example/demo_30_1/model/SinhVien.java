package com.example.demo_30_1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class SinhVien {
    @Id
    private String id;

    @NotBlank(message = "Tên là bắt buộc")
    private String ten;

    @Min(value = 18, message = "Tuổi phải lớn hơn hoặc bằng 18")
    @Max(value = 100, message = "Tuổi phải nhỏ hơn hoặc bằng 100")
    private int tuoi;

    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Khoa phải là chữ")
    private String khoa;

    private boolean isDeleted = false;

    public SinhVien() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
