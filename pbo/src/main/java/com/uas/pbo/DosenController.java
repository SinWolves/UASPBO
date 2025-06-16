package com.uas.pbo;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.uas.pbo.model.User;

@Controller
public class DosenController {


    @GetMapping("dosen/Class_list")
    public String classList(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("name", user.getName());
        return "dosen/Class_list"; 
    }


}