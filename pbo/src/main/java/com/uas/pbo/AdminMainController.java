package com.uas.pbo;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.uas.pbo.model.User;

@Controller
public class AdminMainController {
    @GetMapping("/admin/Class-list")
    public String classList(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("name", user.getName());
        return "admin/Class_list"; 
    }

    @GetMapping("/admin/mahasiswa")
    public String mahasiswa(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("name", user.getName());
        return "admin/mahasiswa"; 
    }
}
