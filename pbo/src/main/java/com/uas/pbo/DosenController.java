package com.uas.pbo;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.uas.pbo.model.User;
import com.uas.pbo.model.Dosen;
import com.uas.pbo.repository.DosenRepository;


@Controller
public class DosenController {

    private DosenRepository dosenRepository;

    public DosenController(DosenRepository dosenRepository) {
        this.dosenRepository = dosenRepository;
    }

    @GetMapping("/dosen/home")
    public String getMethodName(@AuthenticationPrincipal User user, Model model) {
        List<Dosen> approvedClasses = dosenRepository.findByNipAndStatus(user.getIdentifier(), "APPROVED");
        model.addAttribute("name", user.getName());
        model.addAttribute("classs", approvedClasses);
        return "dosen/home";
    }
    

    @GetMapping("dosen/Class_list")
    public String classList(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("name", user.getName());
        return "dosen/Class_list"; 
    }


}