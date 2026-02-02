package com.example.demo_30_1.controller;

import com.example.demo_30_1.model.Comment;
import com.example.demo_30_1.model.SinhVien;
import com.example.demo_30_1.repository.CommentRepository;
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
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @GetMapping
    public String listComments(Model model) {
        List<Comment> comments = commentRepository.findAll();
        model.addAttribute("comments", comments);
        return "comment-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("comment", new Comment());
        model.addAttribute("sinhViens", sinhVienRepository.findAll());
        model.addAttribute("isEdit", false);
        return "form-comment";
    }

    @PostMapping("/add")
    public String addComment(@Valid @ModelAttribute("comment") Comment comment, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sinhViens", sinhVienRepository.findAll());
            model.addAttribute("isEdit", false);
            return "form-comment";
        }
        commentRepository.save(comment);
        return "redirect:/comments";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Comment> c = commentRepository.findById(id);
        if (c.isPresent()) {
            model.addAttribute("comment", c.get());
            model.addAttribute("sinhViens", sinhVienRepository.findAll());
            model.addAttribute("isEdit", true);
            return "form-comment";
        }
        return "redirect:/comments";
    }

    @PostMapping("/edit/{id}")
    public String updateComment(@PathVariable("id") Long id, @Valid @ModelAttribute("comment") Comment comment, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sinhViens", sinhVienRepository.findAll());
            model.addAttribute("isEdit", true);
            return "form-comment";
        }
        comment.setId(id);
        commentRepository.save(comment);
        return "redirect:/comments";
    }

    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable("id") Long id) {
        commentRepository.deleteById(id);
        return "redirect:/comments";
    }
}
