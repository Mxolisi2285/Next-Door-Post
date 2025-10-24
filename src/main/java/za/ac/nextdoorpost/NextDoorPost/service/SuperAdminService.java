package za.ac.nextdoorpost.NextDoorPost.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.ac.nextdoorpost.NextDoorPost.model.Role;
import za.ac.nextdoorpost.NextDoorPost.model.User;
import za.ac.nextdoorpost.NextDoorPost.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SuperAdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SuperAdminService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Get all admins
    public List<User> getAllAdmins() {
        return userRepository.findAll()
                .stream()
                .filter(u -> u.getRole() == Role.ADMIN || u.getRole() == Role.SUPER_ADMIN)
                .toList();
    }

    // Create a new admin
    public boolean createAdmin(String fullName, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            return false; // admin with email already exists
        }

        User admin = new User(fullName, email, passwordEncoder.encode(password), Role.ADMIN);
        userRepository.save(admin);
        return true;
    }

    // Delete admin
    public boolean deleteAdmin(Long id) {
        Optional<User> adminOpt = userRepository.findById(id);
        if (adminOpt.isPresent()) {
            userRepository.delete(adminOpt.get());
            return true;
        }
        return false;
    }

    // ✅ Validate super admin login
    public boolean validateSuperAdmin(String email, String rawPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Check if role is SUPER_ADMIN and password matches
            return user.getRole() == Role.SUPER_ADMIN && passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }
}
