package com.uas.pbo;

import com.uas.pbo.exception.DuplicateApplicationException;
// Import the missing classes
import com.uas.pbo.model.ClassList;
import com.uas.pbo.model.Mahasiswa;
import com.uas.pbo.model.User;
import com.uas.pbo.repository.ClassListRepository;
import com.uas.pbo.repository.MahasiswaRepository;
import com.uas.pbo.service.MahasiswaService;

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

    // MODIFIED: The controller now depends on the Service, not the Repository for this logic.
    @Autowired
    private MahasiswaService mahasiswaService;

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
    // MODIFY THIS METHOD
    @GetMapping("/mahasiswa/home")
    public String mahasiswaHome(@AuthenticationPrincipal User user, Model model) {
        // This part is still correct
        model.addAttribute("name", user.getName());

        // Call the service to get the list of approved enrollments
        List<Mahasiswa> approvedEnrollments = mahasiswaService.getApprovedClassesForStudent(user.getIdentifier());

        // Add the list to the model so the HTML can use it
        model.addAttribute("enrollments", approvedEnrollments);

        return "mahasiswa/home";
    }

    // MODIFIED: This method is now much simpler.
    @PostMapping("/mahasiswa/apply")
    public String applyForClass(@RequestParam("courseCode") String courseCode,
                                @AuthenticationPrincipal User user,
                                RedirectAttributes redirectAttributes) {

        String nim = user.getIdentifier();

        try {
            // 1. Call the service to perform the business logic.
            mahasiswaService.applyForClass(nim, courseCode);
            // 2. If it succeeds without error, set the success message.
            redirectAttributes.addFlashAttribute("successMessage", "Successfully applied for class " + courseCode + ". Waiting for approval.");
        
        } catch (DuplicateApplicationException e) {
            // 3. If the service throws our specific exception, set the error message.
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/mahasiswa/Class_list";
    }
}
