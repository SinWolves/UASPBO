package com.uas.pbo;


import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.Optional;

import java.util.List;

import com.uas.pbo.model.Dosen;
import com.uas.pbo.model.Mahasiswa;
import com.uas.pbo.model.User;
import com.uas.pbo.exception.DuplicateApplicationException;
import com.uas.pbo.model.ClassList;
import com.uas.pbo.repository.DosenRepository;
import com.uas.pbo.repository.MahasiswaRepository;
import com.uas.pbo.service.ClassListService;
import com.uas.pbo.repository.ClassListRepository;

@Controller
public class AdminMainController {

    private DosenRepository dosenRepository;
    private MahasiswaRepository mahasiswaRepository;
    private final ClassListService classListService;
    private  ClassListRepository classListRepository;

    //Dosen
    public AdminMainController(DosenRepository dosenRepository, ClassListService classListService, MahasiswaRepository mahasiswaRepository
                                , ClassListRepository classListRepository) {
        this.mahasiswaRepository = mahasiswaRepository;
        this.classListService = classListService;
        this.dosenRepository = dosenRepository;
        this.classListRepository = classListRepository;
    }

    // Tabel Dosen
    @GetMapping("/admin/home")
    public String adminHome(@AuthenticationPrincipal User user, Model model) {
        List<Dosen> dosenList = dosenRepository.findAll();
        model.addAttribute("name", user.getName());
        model.addAttribute("dosenList", dosenList);
        return "admin/home";
    }

    // Tabel Matkuliah
    @GetMapping("/admin/Class-list")
    public String classList(@AuthenticationPrincipal User user, Model model) {
        List<ClassList> classLists = classListService.getAllClasses();
        model.addAttribute("name", user.getName());
        model.addAttribute("classLists", classLists);
        return "admin/Class_list"; 
    }

     // NEW: This method handles the "Add Class" form submission
    @PostMapping("/admin/add-class")
    public String addClass(@ModelAttribute ClassList classList, RedirectAttributes redirectAttributes) {
        // @ModelAttribute automatically creates a ClassList object from the form fields.
        try {
            // 1. We ATTEMPT to add the class by calling the service.
            classListService.addClass(classList);
            return "redirect:/admin/Class-list";
        } catch (DuplicateApplicationException e) {
            // 2. If an error occurs, we set an ERROR message.
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add class: " + e.getMessage());
            return "redirect:/admin/Class-list"; // Redirect back to the class list page
        }
    }

    // NEW: This method handles the "Delete" button click
    @PostMapping("/admin/delete-class")
    public String deleteClass(@RequestParam("courseCode") String courseCode, RedirectAttributes redirectAttributes) {
        
        // The try...catch block is essential.
        try {
            // 1. We ATTEMPT to delete the class by calling the service.
            classListService.deleteClass(courseCode);
            
            // 2. If the line above succeeds without error, we set a SUCCESS message.
            redirectAttributes.addFlashAttribute("successMessage", "Class " + courseCode + " was deleted successfully.");
        
        } catch (IllegalStateException e) {
            // 3. If the service throws our specific exception, we CATCH it here.
            // Instead of crashing, we set an ERROR message.
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        
        // 4. In either case (success or caught error), we redirect the user back to the list page.
        return "redirect:/admin/Class-list";
    }

    @GetMapping("/admin/mahasiswa")
    public String mahasiswa(@AuthenticationPrincipal User user, Model model) {
        List<Mahasiswa> mahasiswaList = mahasiswaRepository.findAll();
        model.addAttribute("name", user.getName());
        model.addAttribute("mahasiswaList", mahasiswaList);
        return "admin/mahasiswa"; 
    }


    @PostMapping("/admin/approve")
    public String approveDosen(@RequestParam Long nip,
                                @RequestParam String coursecode,
                                @RequestParam String role,
                                @RequestParam String action) {
                                    
        Optional<Dosen> optionalDosen = dosenRepository.findById(nip);
        Optional<Mahasiswa> optionalMahasiswa = mahasiswaRepository.findById(nip);
        Optional<ClassList> classLists = classListRepository.findByCourseCode(coursecode);

        String status = action.equals("approve") ? "APPROVED" : "DECLINED";

        if (role.equals("DOSEN")){
            if (optionalDosen.isPresent() && classLists.isPresent()) {
                Dosen dosen = optionalDosen.get();
                ClassList classList = classLists.get();
                
                // optional check for matching mata kuliah
                if (dosen.getCourseCode().equals(coursecode)) {
                    dosen.setStatus(status);

                    if (status.equals("APPROVED")) {
                        classList.setLecturer(dosen.getUser().getName());
                    }

                    classListRepository.save(classList);
                    dosenRepository.save(dosen);

                    return "redirect:/admin/home"; 
                }
            }
        } 
        else if (role.equals("MAHASISWA")) {
            if (optionalMahasiswa.isPresent()) {
                Mahasiswa mahasiswa = optionalMahasiswa.get();
                
                // optional check for matching mata kuliah
                if (mahasiswa.getCourseCode().equals(coursecode)) {
                    if (status.equals("DECLINED")) {
                        mahasiswaRepository.delete(mahasiswa);
                        return "redirect:/admin/mahasiswa"; 
                    }
                    else{
                        mahasiswa.setStatus(status);
                        mahasiswaRepository.save(mahasiswa);
                        return "redirect:/admin/mahasiswa"; 
                    }
                }
            }
        }

        return "redirect:/admin/home";
    }
}
