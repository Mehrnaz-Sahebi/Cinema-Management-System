package org.example.moviereservationsystem.configs;

import lombok.RequiredArgsConstructor;
import org.example.moviereservationsystem.authentication.MyPasswordEncoder;
import org.example.moviereservationsystem.authentication.jwt.JwtAuthFilter;
import org.example.moviereservationsystem.user.UserController;
import org.example.moviereservationsystem.user.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    @Autowired
    private MyPasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((requests) -> {
            (requests
                    .requestMatchers("/auth")
                    .permitAll()
                    .requestMatchers(UserController.SLASH_ADD_USER)
                    .permitAll()
                    .requestMatchers("/admin/**")
                    .hasRole(UserRoles.ADMIN)
                    .anyRequest())
                    .authenticated();
        })
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider())
                .sessionManagement(
                        httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.csrf(csrf -> csrf.disable());
        return (SecurityFilterChain)http.build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(jwtAuthFilter.getUserService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
