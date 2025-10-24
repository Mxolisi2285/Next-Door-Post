package za.ac.nextdoorpost.NextDoorPost.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.ac.nextdoorpost.NextDoorPost.model.Announcement;
import za.ac.nextdoorpost.NextDoorPost.model.ContactMessage;
import za.ac.nextdoorpost.NextDoorPost.model.Event;
import za.ac.nextdoorpost.NextDoorPost.repository.AnnouncementRepository;
import za.ac.nextdoorpost.NextDoorPost.repository.EventRepository;
import za.ac.nextdoorpost.NextDoorPost.service.EmailService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class CommunityController {

    private final AnnouncementRepository announcementRepository;
    private final EventRepository eventRepository;
    private final EmailService emailService;

    @Value("${app.upload.dir:${user.dir}/uploads/}")
    private String uploadDir;

    public CommunityController(AnnouncementRepository announcementRepository,
                               EventRepository eventRepository,
                               EmailService emailService) {
        this.announcementRepository = announcementRepository;
        this.eventRepository = eventRepository;
        this.emailService = emailService;
    }

    // ======= HOME PAGE WITH CONTACT FORM =======
    @GetMapping("/")
    public String home(Model model) {
        // ✅ Fetch ONLY approved announcements and events
        model.addAttribute("announcements", announcementRepository.findByApprovedTrue());
        model.addAttribute("events", eventRepository.findByApprovedTrue());
        model.addAttribute("contactMessage", new ContactMessage()); // for the contact form
        return "home";
    }

    @PostMapping("/contact/send")
    public String sendContactMessage(@ModelAttribute ContactMessage contactMessage,
                                     RedirectAttributes redirectAttributes) {
        try {
            emailService.sendContactMessage(contactMessage);
            redirectAttributes.addFlashAttribute("successMessage", "Message sent successfully! We will contact you soon.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error sending message. Please try again.");
            e.printStackTrace();
        }
        return "redirect:/"; // Redirect back to home page, message disappears after refresh
    }

    // ======= ANNOUNCEMENTS PAGE =======
    @GetMapping("/announcements")
    public String announcements(Model model) {
        model.addAttribute("announcements", announcementRepository.findAll());
        return "announcements";
    }

    // ======= EVENTS PAGE =======
    @GetMapping("/events")
    public String events(Model model) {
        model.addAttribute("events", eventRepository.findAll());
        return "events";
    }

    // ======= LOGIN PAGE =======
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // ======= GALLERY PAGE =======
    @GetMapping("/gallery")
    public String gallery() {
        return "gallery";
    }

    // ======= ABOUT PAGE =======
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    // ======= SUBMIT EVENT FORM =======
    @GetMapping("/submit-event")
    public String showSubmitEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "submit-event";
    }

    @PostMapping("/submit-event")
    public String submitEvent(
            @Valid @ModelAttribute("event") Event event,
            BindingResult bindingResult,
            @RequestParam("image") MultipartFile image,
            Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            return "submit-event";
        }

        // Handle image upload (same as announcement)
        if (image != null && !image.isEmpty()) {
            String originalFilename = image.getOriginalFilename();
            if (originalFilename == null || originalFilename.isBlank()) {
                model.addAttribute("errorMessage", "Invalid file name.");
                return "submit-event";
            }

            String cleanFilename = originalFilename.replaceAll("[^a-zA-Z0-9._-]", "_");
            String filename = System.currentTimeMillis() + "_" + cleanFilename;
            Path uploadPath = Paths.get(uploadDir).resolve(filename).normalize();

            if (!uploadPath.startsWith(Paths.get(uploadDir).toAbsolutePath())) {
                model.addAttribute("errorMessage", "Invalid file path.");
                return "submit-event";
            }

            Files.createDirectories(uploadPath.getParent());
            image.transferTo(uploadPath.toFile());
            event.setImagePath(filename);
        }

        eventRepository.save(event);

        // ✅ Add success message and re-render same page
        model.addAttribute("successMessage", "Event submitted successfully! Redirecting...");
        model.addAttribute("event", new Event()); // reset form
        return "submit-event";
    }

    // ======= SUBMIT ANNOUNCEMENT FORM =======
    @GetMapping("/submit-announcement")
    public String showSubmitAnnouncementForm(Model model) {
        model.addAttribute("announcement", new Announcement());
        return "submit-announcement";
    }

    @PostMapping("/submit-announcement")
    public String submitAnnouncement(
            @Valid @ModelAttribute("announcement") Announcement announcement,
            BindingResult bindingResult,
            @RequestParam("image") MultipartFile image,
            Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            return "submit-announcement";
        }

        announcement.setDate(java.time.LocalDate.now().toString());

        if (image != null && !image.isEmpty()) {
            String originalFilename = image.getOriginalFilename();
            if (originalFilename == null || originalFilename.isBlank()) {
                model.addAttribute("errorMessage", "Invalid file name.");
                return "submit-announcement";
            }

            String cleanFilename = originalFilename.replaceAll("[^a-zA-Z0-9._-]", "_");
            String filename = System.currentTimeMillis() + "_" + cleanFilename;
            Path uploadPath = Paths.get(uploadDir).resolve(filename).normalize();

            if (!uploadPath.startsWith(Paths.get(uploadDir).toAbsolutePath())) {
                model.addAttribute("errorMessage", "Invalid file path.");
                return "submit-announcement";
            }

            Files.createDirectories(uploadPath.getParent());
            image.transferTo(uploadPath.toFile());
            announcement.setImagePath(filename);
        }

        announcementRepository.save(announcement);

        // ✅ Add success message and re-render the SAME page (not redirect yet)
        model.addAttribute("successMessage", "Announcement submitted successfully! Redirecting...");
        model.addAttribute("announcement", new Announcement()); // reset form
        return "submit-announcement"; // render same page with success message
    }
}
