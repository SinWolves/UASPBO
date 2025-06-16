package com.uas.pbo;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminMainController {
    @GetMapping("adminHome")
    public String home(Model model) {
        return "admin/home"; 
    }

    @GetMapping("adminClass-list")
    public String classList(Model model) {
        return "admin/Class_list"; 
    }

    @GetMapping("adminMahasiswa")
    public String mahasiswa(Model model) {
        return "admin/mahasiswa"; 
    }
}
