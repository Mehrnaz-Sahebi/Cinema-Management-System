package org.example.moviereservationsystem.configs;

import lombok.RequiredArgsConstructor;
import org.example.moviereservationsystem.authentication.MyPasswordEncoder;
import org.example.moviereservationsystem.authentication.jwt.JwtAuthFilter;
import org.example.moviereservationsystem.user.UserController;
import org.example.moviereservationsystem.user.UserRoles;
import org.example.moviereservationsystem.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
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
    @Autowired
    private final JwtAuthFilter jwtAuthFilter;
    @Autowired
    private MyPasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((requests) -> {
            (requests
                    .requestMatchers("/auth")
                    .permitAll()
                    .requestMatchers(UserController.SLASH_ADD_USER)
                    .permitAll()
                    .requestMatchers("/admin/**")
                    .hasAuthority(UserRoles.ADMIN)
//                    .permitAll()
                    .anyRequest())
                    .authenticated();
//                    .permitAll();
        })
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider())
                .sessionManagement(
                        httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.csrf(csrf -> csrf.disable());
        http.formLogin(Customizer.withDefaults());
        return (SecurityFilterChain)http.build();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
