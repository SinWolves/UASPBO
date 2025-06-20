package com.uas.pbo;


import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Optional;

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


    @PostMapping("/admin/approve")
    public String approveDosen(@RequestParam String nip,
                                        @RequestParam String mataKuliah) {
        Optional<Dosen> optionalDosen = dosenRepository.findById(nip);

        if (optionalDosen.isPresent()) {
            Dosen dosen = optionalDosen.get();
            
            // optional check for matching mata kuliah
            if (dosen.getMataKuliah().equals(mataKuliah)) {
                dosen.setStatus("APPROVED");
                dosenRepository.save(dosen);
                return "redirect:/admin/home"; 
            }
        }

        return "redirect:/admin/home";
    }
}
