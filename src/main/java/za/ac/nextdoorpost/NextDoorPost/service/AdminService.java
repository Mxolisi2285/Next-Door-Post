package za.ac.nextdoorpost.NextDoorPost.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.ac.nextdoorpost.NextDoorPost.model.Announcement;
import za.ac.nextdoorpost.NextDoorPost.model.Event;
import za.ac.nextdoorpost.NextDoorPost.model.Role;
import za.ac.nextdoorpost.NextDoorPost.model.User;
import za.ac.nextdoorpost.NextDoorPost.repository.AnnouncementRepository;
import za.ac.nextdoorpost.NextDoorPost.repository.EventRepository;
import za.ac.nextdoorpost.NextDoorPost.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final EventRepository eventRepository;
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(EventRepository eventRepository,
                        AnnouncementRepository announcementRepository,
                        UserRepository userRepository,
                        PasswordEncoder passwordEncoder) {
        this.eventRepository = eventRepository;
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /* ---------------- EVENTS ---------------- */

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void approveEvent(Long id) {
        eventRepository.findById(id).ifPresent(event -> {
            event.setApproved(true);
            eventRepository.save(event);
        });
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    /* ---------------- ANNOUNCEMENTS ---------------- */

    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    public void approveAnnouncement(Long id) {
        announcementRepository.findById(id).ifPresent(announcement -> {
            announcement.setApproved(true);
            announcementRepository.save(announcement);
        });
    }

    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }

    /* ---------------- ADMIN ---------------- */

    public Optional<User> findUserByEmailAndRole(String email, Role role) {
        return userRepository.findByEmailAndRole(email, role);
    }

    public Optional<User> findUserByIdAndRole(Long id, Role role) {
        return userRepository.findByIdAndRole(id, role);
    }

    public void updateUserProfile(User user, String password) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setFullName(user.getFullName());

        if (password != null && !password.isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(password));
        }

        userRepository.save(existingUser);
    }
}
