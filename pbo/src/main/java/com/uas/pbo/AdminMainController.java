package com.uas.pbo;


import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import com.uas.pbo.model.Dosen;
import com.uas.pbo.model.User;
import com.uas.pbo.repository.DosenRepository;

@Controller
public class AdminMainController {

    private final DosenRepository dosenRepository;

    public AdminMainController(DosenRepository dosenRepository) {
        this.dosenRepository = dosenRepository;
    }

    @GetMapping("/admin/home")
    public String adminHome(@AuthenticationPrincipal User user, Model model) {
        List<Dosen> dosenList = dosenRepository.findAll();
        model.addAttribute("name", user.getName());
        model.addAttribute("dosenList", dosenList);
        return "admin/home";
    }

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
