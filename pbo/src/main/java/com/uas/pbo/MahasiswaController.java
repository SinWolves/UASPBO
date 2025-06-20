package com.uas.pbo;

// Import the missing classes
import com.uas.pbo.model.ClassList;
import com.uas.pbo.model.User;
import com.uas.pbo.repository.ClassListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List; // Make sure to import List

@Controller
public class MahasiswaController {

    // STEP 1: Inject the repository
    @Autowired
    private ClassListRepository classListRepository;

    @GetMapping("mahasiswa/Class_list")
    public String classList(@AuthenticationPrincipal User user, Model model) {
        // STEP 2: Add all the required data to the model in one place

        // 1. Get the user's name (this was already here)
        model.addAttribute("name", user.getName());

        // 2. Get the list of classes (this is the logic from the other controller)
        List<ClassList> classLists = classListRepository.findAll();
        model.addAttribute("classLists", classLists);

        // Return the view. It now has BOTH name and classLists.
        return "mahasiswa/Class_list";
    }

    // You can have other methods for the Mahasiswa role here...
    // For example, a home page controller
    @GetMapping("mahasiswa/home")
    public String mahasiswaHome(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("name", user.getName());
        // ... add any data needed for the home page
        return "mahasiswa/home"; // assuming you have a home.html
    }
}