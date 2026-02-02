package com.example.demo_30_1.controller;

import com.example.demo_30_1.model.SinhVien;
import com.example.demo_30_1.repository.SinhVienRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sinhvien")
public class SinhVienController {

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @GetMapping
    public String listSinhVien(Model model) {
        List<SinhVien> list = sinhVienRepository.findAll();
        model.addAttribute("danhSachSinhVien", list);
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
        
        // Auto-increment ID logic (String ID)
        List<SinhVien> all = sinhVienRepository.findAll();
        int maxId = 0;
        for (SinhVien sv : all) {
            try {
                int idVal = Integer.parseInt(sv.getId());
                if (idVal > maxId) maxId = idVal;
            } catch (NumberFormatException e) {
                // Ignore non-numeric IDs
            }
        }
        sinhVien.setId(String.valueOf(maxId + 1));
        
        sinhVienRepository.save(sinhVien);
        
        model.addAttribute("message", "Thêm sinh viên thành công!");
        model.addAttribute("sinhVien", sinhVien);
        return "result-sinhvien";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        Optional<SinhVien> sv = sinhVienRepository.findById(id);
        if (sv.isPresent()) {
            model.addAttribute("sinhVien", sv.get());
            model.addAttribute("isEdit", true);
            return "form-sinhvien";
        }
        return "redirect:/sinhvien";
    }

    @PostMapping("/edit/{id}")
    public String updateSinhVien(@PathVariable("id") String id, @Valid @ModelAttribute("sinhVien") SinhVien sinhVien, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", true);
            return "form-sinhvien";
        }
        sinhVien.setId(id); // Ensure ID is preserved
        sinhVienRepository.save(sinhVien);
        return "redirect:/sinhvien";
    }

    @GetMapping("/delete/{id}")
    public String deleteSinhVien(@PathVariable("id") String id) {
        Optional<SinhVien> sv = sinhVienRepository.findById(id);
        if (sv.isPresent()) {
            SinhVien s = sv.get();
            s.setDeleted(true); // Soft delete
            sinhVienRepository.save(s);
        }
        return "redirect:/sinhvien";
    }
}
