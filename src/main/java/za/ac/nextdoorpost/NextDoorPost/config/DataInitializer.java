package za.ac.nextdoorpost.NextDoorPost.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import za.ac.nextdoorpost.NextDoorPost.model.Role;
import za.ac.nextdoorpost.NextDoorPost.model.User;
import za.ac.nextdoorpost.NextDoorPost.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeSuperAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Create Super Admin
            String superAdminEmail = "superadmin@nextdoorpost.co.za";
            if (userRepository.findByEmail(superAdminEmail).isEmpty()) {
                User superAdmin = new User(
                        "System Super Admin",
                        superAdminEmail,
                        passwordEncoder.encode("superadmin123"),
                        Role.SUPER_ADMIN
                );
                userRepository.save(superAdmin);
                System.out.println("✅ Super Admin account created: " + superAdminEmail);
            } else {
                System.out.println("ℹ️ Super Admin already exists.");
            }

            // Create Test Admin
            String adminEmail = "admin@test.com";
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                User admin = new User(
                        "Test Admin",
                        adminEmail,
                        passwordEncoder.encode("admin123"),
                        Role.ADMIN
                );
                userRepository.save(admin);
                System.out.println("✅ Admin account created: " + adminEmail);
            } else {
                System.out.println("ℹ️ Admin already exists.");
            }
        };
    }
}