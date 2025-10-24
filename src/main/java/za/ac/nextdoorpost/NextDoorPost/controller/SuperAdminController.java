package za.ac.nextdoorpost.NextDoorPost.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.ac.nextdoorpost.NextDoorPost.service.SuperAdminService;

@Controller
@RequestMapping("/super-admin")
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    public SuperAdminController(SuperAdminService superAdminService) {
        this.superAdminService = superAdminService;
    }

    // 🏠 Dashboard — now protected by Spring Security
    @GetMapping("/super-admin-dashboard")
    public String dashboard(Model model) {
        model.addAttribute("admins", superAdminService.getAllAdmins());
        return "super-admin-dashboard";
    }

    // ➕ Add new Admin
    @PostMapping("/add-admin")
    public String addAdmin(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String password,
            RedirectAttributes redirectAttributes
    ) {
        boolean created = superAdminService.createAdmin(fullName, email, password);
        if (created) {
            redirectAttributes.addFlashAttribute("successMessage", "Admin created successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Admin with this email already exists.");
        }
        return "redirect:/super-admin/super-admin-dashboard";
    }

    // ❌ Delete Admin
    @GetMapping("/delete-admin/{id}")
    public String deleteAdmin(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean deleted = superAdminService.deleteAdmin(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "Admin deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Admin could not be deleted.");
        }
        return "redirect:/super-admin/super-admin-dashboard";
    }

    // 🔐 Login page — just show the form (Spring Security handles POST)
    @GetMapping("/login")
    public String loginPage(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String logout,
            Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid email or password.");
        }
        if (logout != null) {
            model.addAttribute("successMessage", "You have been logged out.");
        }
        return "login";
    }
}