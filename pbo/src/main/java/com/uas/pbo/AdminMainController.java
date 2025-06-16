package com.uas.pbo;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.uas.pbo.model.User;

@Controller
public class AdminMainController {
    @GetMapping("adminHome")
    public String home(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("name", user.getName());
        return "admin/home"; 
    }

    @GetMapping("adminClass-list")
    public String classList(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("name", user.getName());
        return "admin/Class_list"; 
    }

    @GetMapping("adminMahasiswa")
    public String mahasiswa(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("name", user.getName());
        return "admin/mahasiswa"; 
    }
}
