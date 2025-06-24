package com.uas.pbo;

import org.springframework.security.core.Authentication; // Use Authentication for more robust role checking
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uas.pbo.service.UserService;

@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
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
                            @RequestParam String role,
                            org.springframework.ui.Model model) {
        try {
            userService.registerUser(identifier, email, name, password, role);
            return "redirect:/login";
        } catch (RuntimeException e) {
            // Kirim pesan error ke halaman signup
            model.addAttribute("errorMessage", e.getMessage());
            return "signup";
        }
    }


    // This is the only role-based logic that should be in this controller.
    @GetMapping("/redirect")
    public String redirect(Authentication authentication) { // Switched to Authentication object
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/home";
        } else if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_DOSEN"))) {
            return "redirect:/dosen/home";
        } else if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MAHASISWA"))) {
            return "redirect:/mahasiswa/home";
        } else {
            return "redirect:/login";
        }
    }

    // The methods for mahasiswaHome and dosenHome have been removed.
}