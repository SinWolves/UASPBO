package com.uas.pbo;

// Import the missing classes
import com.uas.pbo.model.ClassList;
import com.uas.pbo.model.Mahasiswa;
import com.uas.pbo.model.User;
import com.uas.pbo.repository.ClassListRepository;
import com.uas.pbo.repository.MahasiswaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List; // Make sure to import List

@Controller
public class MahasiswaController {

    // STEP 1: Inject the repository
    @Autowired
    private ClassListRepository classListRepository;

    // REPO FROM 
    @Autowired
    private MahasiswaRepository mahasiswaRepository;

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

        // NEW: This method handles the 'Apply Class' button submission
    @PostMapping("/mahasiswa/apply")
    public String applyForClass(@RequestParam("courseCode") String courseCode,
                                @AuthenticationPrincipal User user,
                                RedirectAttributes redirectAttributes) {

        String nim = user.getIdentifier();

        // Check if the student has already applied for this class
        if (mahasiswaRepository.existsByNimAndCourseCode(nim, courseCode)) {
            redirectAttributes.addFlashAttribute("errorMessage", "You have already applied for this class.");
            return "redirect:/mahasiswa/Class_list";
        }

        // Create a new enrollment record with "PENDING" status
        Mahasiswa newEnrollment = new Mahasiswa(nim, courseCode, "PENDING");
        mahasiswaRepository.save(newEnrollment);

        // Send a success message back to the user
        redirectAttributes.addFlashAttribute("successMessage", "Successfully applied for class " + courseCode + ". Waiting for approval.");
        
        return "redirect:/mahasiswa/Class_list";
    }
}
