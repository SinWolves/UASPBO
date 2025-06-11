package com.uas.pbo;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uas.pbo.model.User;
import com.uas.pbo.service.UserService;

@Controller
public class loginController {
    private final UserService userService;

    public loginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@RequestParam String identifier,
                              @RequestParam String email,
                              @RequestParam String name,
                              @RequestParam String password,
                              @RequestParam String role) {
        userService.registerUser(identifier, email, name, password, role);
        return "redirect:/login";
    }

    @GetMapping("/redirect")
    public String redirect(@AuthenticationPrincipal User user) {
        switch (user.getRole()) {
            case "ADMIN":
                return "redirect:/admin/home";
            case "DOSEN":
                return "redirect:/dosen/home";
            case "MAHASISWA":
                return "redirect:/mahasiswa/home";
            default:
                return "redirect:/login";
        }
    }

    @GetMapping("/admin/home")
    public String adminHome(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("name", user.getName());
        return "admin/home";
    }

    @GetMapping("/dosen/home")
    public String dosenHome(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("name", user.getName());
        return "dosen/home";
    }

    @GetMapping("/mahasiswa/home")
    public String mahasiswaHome(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("name", user.getName());
        return "mahasiswa/home";
    }
}