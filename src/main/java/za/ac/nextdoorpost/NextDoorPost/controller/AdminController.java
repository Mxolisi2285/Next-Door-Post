package za.ac.nextdoorpost.NextDoorPost.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.ac.nextdoorpost.NextDoorPost.model.Announcement;
import za.ac.nextdoorpost.NextDoorPost.model.Event;
import za.ac.nextdoorpost.NextDoorPost.model.Role;
import za.ac.nextdoorpost.NextDoorPost.model.User;
import za.ac.nextdoorpost.NextDoorPost.service.AdminService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // 🏠 Admin Dashboard
    @GetMapping("/admin-dashboard")
    public String dashboard(Model model) {
        List<Announcement> announcements = adminService.getAllAnnouncements();
        List<Event> events = adminService.getAllEvents();

        model.addAttribute("announcements", announcements);
        model.addAttribute("events", events);

        model.addAttribute("totalAnnouncements", announcements.size());
        model.addAttribute("totalEvents", events.size());

        long pendingApprovals = announcements.stream().filter(a -> !a.isApproved()).count()
                + events.stream().filter(e -> !e.isApproved()).count();
        model.addAttribute("pendingApprovals", pendingApprovals);

        return "admin-dashboard";
    }

    /* ---------------- ANNOUNCEMENTS ---------------- */
    @GetMapping("/approve-announcement/{id}")
    public String approveAnnouncement(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        adminService.approveAnnouncement(id);
        redirectAttributes.addFlashAttribute("successMessage", "Announcement approved successfully!");
        return "redirect:/admin/admin-dashboard";
    }

    @GetMapping("/delete-announcement/{id}")
    public String deleteAnnouncement(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        adminService.deleteAnnouncement(id);
        redirectAttributes.addFlashAttribute("successMessage", "Announcement deleted successfully!");
        return "redirect:/admin/admin-dashboard";
    }

    /* ---------------- EVENTS ---------------- */
    @GetMapping("/approve-event/{id}")
    public String approveEvent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        adminService.approveEvent(id);
        redirectAttributes.addFlashAttribute("successMessage", "Event approved successfully!");
        return "redirect:/admin/admin-dashboard";
    }

    @GetMapping("/delete-event/{id}")
    public String deleteEvent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        adminService.deleteEvent(id);
        redirectAttributes.addFlashAttribute("successMessage", "Event deleted successfully!");
        return "redirect:/admin/admin-dashboard";
    }

    /* ---------------- ADMIN PROFILE ---------------- */
    @GetMapping("/update-profile")
    public String showUpdateProfile(Model model, Principal principal) {
        String email = principal.getName();

        Optional<User> optionalAdmin = adminService.findUserByEmailAndRole(email, Role.ADMIN);
        if (optionalAdmin.isEmpty()) {
            return "redirect:/login?error";
        }

        model.addAttribute("adminUser", optionalAdmin.get());
        return "update-profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute("adminUser") User adminUser,
                                @RequestParam(required = false) String password,
                                RedirectAttributes redirectAttributes) {

        Optional<User> optionalAdmin = adminService.findUserByIdAndRole(adminUser.getId(), Role.ADMIN);
        if (optionalAdmin.isEmpty()) {
            return "redirect:/login?error";
        }

        adminService.updateUserProfile(adminUser, password);
        redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
        return "redirect:/admin/admin-dashboard";
    }
}
