// File: za/ac/nextdoorpost/NextDoorPost/config/CustomAuthenticationSuccessHandler.java
package za.ac.nextdoorpost.NextDoorPost.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Check roles and redirect accordingly
        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        boolean isSuperAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_SUPER_ADMIN"));

        if (isSuperAdmin) {
            response.sendRedirect("/super-admin/super-admin-dashboard");
        } else if (isAdmin) {
            response.sendRedirect("/admin/admin-dashboard");
        } else {
            // Fallback for any other authenticated user
            response.sendRedirect("/login?error=true");
        }
    }
}