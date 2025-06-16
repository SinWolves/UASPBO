package com.uas.pbo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MahasiswaController {

    @GetMapping("home")
    public String home(Model model) {
        // isi model jika perlu
        return "mahasiswa/home"; // akan load src/main/resources/templates/home.html
    }

    @GetMapping("class-list")
    public String classList(Model model) {
        return "mahasiswa/Class_list"; // akan load Class_list.html dari /templates
    }
}
