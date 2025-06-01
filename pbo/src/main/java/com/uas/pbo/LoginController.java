package com.uas.pbo;

import com.uas.pbo.repository.userRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {

    private final userRepository userRepository;

    public loginController(userRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Add this method to serve the login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login.html"; 
    }
}