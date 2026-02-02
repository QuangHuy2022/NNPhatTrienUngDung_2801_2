package com.example.demo_30_1.controller;

import com.example.demo_30_1.model.SinhVien;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sinhvien")
public class SinhVienController {

    private static List<SinhVien> sinhVienList = new ArrayList<>();
    private static int idCounter = 1;

    @GetMapping
    public String listSinhVien(Model model) {
        model.addAttribute("danhSachSinhVien", sinhVienList);
        return "sinhvien-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("sinhVien", new SinhVien());
        model.addAttribute("isEdit", false);
        return "form-sinhvien";
    }

    @PostMapping("/add")
    public String addSinhVien(@Valid @ModelAttribute("sinhVien") SinhVien sinhVien, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "form-sinhvien";
        }
        sinhVien.setId(idCounter++);
        sinhVienList.add(sinhVien);
        model.addAttribute("message", "Thêm sinh viên thành công!");
        model.addAttribute("sinhVien", sinhVien);
        return "result-sinhvien";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Optional<SinhVien> sv = sinhVienList.stream().filter(s -> s.getId() == id).findFirst();
        if (sv.isPresent()) {
            model.addAttribute("sinhVien", sv.get());
            model.addAttribute("isEdit", true);
            return "form-sinhvien";
        }
        return "redirect:/sinhvien";
    }

    @PostMapping("/edit/{id}")
    public String updateSinhVien(@PathVariable("id") int id, @Valid @ModelAttribute("sinhVien") SinhVien sinhVien, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", true);
            return "form-sinhvien";
        }
        
        for (int i = 0; i < sinhVienList.size(); i++) {
            if (sinhVienList.get(i).getId() == id) {
                sinhVien.setId(id); // Preserve ID
                sinhVienList.set(i, sinhVien);
                break;
            }
        }
        return "redirect:/sinhvien";
    }

    @GetMapping("/delete/{id}")
    public String deleteSinhVien(@PathVariable("id") int id) {
        sinhVienList.removeIf(s -> s.getId() == id);
        return "redirect:/sinhvien";
    }
}
