package com.uas.pbo;

import java.util.List;
import java.util.Optional;
import com.uas.pbo.service.DosenService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uas.pbo.model.User;
import com.uas.pbo.model.Dosen;
import com.uas.pbo.exception.DuplicateApplicationException;
import com.uas.pbo.model.ClassList;
import com.uas.pbo.repository.DosenRepository;
import com.uas.pbo.repository.ClassListRepository;


@Controller
public class DosenController {

    private final DosenService dosenService;

    private DosenRepository dosenRepository;
    private ClassListRepository classListRepository;

    public DosenController(DosenRepository dosenRepository, ClassListRepository classListRepository, DosenService dosenService) {
        this.classListRepository = classListRepository;
        this.dosenRepository = dosenRepository;
        this.dosenService = dosenService;
    }

    @GetMapping("/dosen/home")
    public String getMethodName(@AuthenticationPrincipal User user, Model model) {
        List<Dosen> approvedClasses = dosenRepository.findByNipAndStatus(user.getIdentifier(), "APPROVED");
        model.addAttribute("name", user.getName());
        model.addAttribute("classs", approvedClasses);
        return "dosen/home";
    }
    

    @GetMapping("dosen/Class_list")
    public String classList(@AuthenticationPrincipal User user, Model model) {
        List<ClassList> classLists = classListRepository.findAll();
        model.addAttribute("name", user.getName());
        model.addAttribute("classLists", classLists);
        return "dosen/Class_list"; 
    }

    @PostMapping("/dosen/apply")
    public String applyForClass(@RequestParam("courseCode") String courseCode,
                                @RequestParam("lecturer") String lecturer,
                                @AuthenticationPrincipal User user,
                                RedirectAttributes redirectAttributes) {

        String nip = user.getIdentifier();

        try {
            if (lecturer != null && !lecturer.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "This class already has a lecturer assigned.");
                return "redirect:/dosen/Class_list";
            }else
            {// 1. Call the service to perform the business logic.
            dosenService.applyForClass(nip, courseCode);
            // 2. If it succeeds without error, set the success message.
            redirectAttributes.addFlashAttribute("successMessage", "Successfully applied for class " + courseCode + ". Waiting for approval.");}
        
        } catch (DuplicateApplicationException e) {
            // 3. If the service throws our specific exception, set the error message.
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/dosen/Class_list";
    }
}