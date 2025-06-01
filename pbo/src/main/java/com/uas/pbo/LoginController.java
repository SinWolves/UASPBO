package com.uas.pbo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class LoginController {
    @PostMapping("/login")
    public String login(@RequestParam String identifier, @RequestParam String pwd) {
        //TODO: process POST request
        
        return entity;
    }
    
}
