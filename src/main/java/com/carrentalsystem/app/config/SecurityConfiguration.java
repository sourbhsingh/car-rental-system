package com.carrentalsystem.app.config;

import com.carrentalsystem.app.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for REST APIs
                .authorizeHttpRequests(auth -> auth
                        // ✅ Public REST endpoints
                        .requestMatchers("/api/auth/**").permitAll()

                        // ✅ Public Thymeleaf endpoints
                        .requestMatchers("/", "/auth/login", "/auth/register", "/auth/admin/login", "/api/admin/**", "/error/**").permitAll()

                        // ✅ Static resources
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/images/**", "/uploads/**").permitAll()

                        // ✅ Role-based access
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")

                        // Everything else needs authentication
                        .anyRequest().authenticated()
                )

                // ✅ Login for web users (Thymeleaf)
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login") // Spring Security handles it
                        .successHandler(customAuthenticationSuccessHandler())
                        .failureUrl("/auth/login?error=true")
                        .permitAll()
                )

                // ✅ Logout handling
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )

                // ✅ Access denied page
                .exceptionHandling(ex -> ex.accessDeniedPage("/403"));

        return http.build();
    }

    // 🔐 Encrypt user passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ Redirect after login (only for web form login, not REST)
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (HttpServletRequest request,
                HttpServletResponse response,
                Authentication authentication) -> {

            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

            boolean isUser = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"));

            if (isAdmin) {
                response.sendRedirect("/admin/dashboard");
            } else if (isUser) {
                response.sendRedirect("/user/dashboard");
            } else {
                response.sendRedirect("/403");
            }
        };
    }
}
