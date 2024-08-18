package org.example.moviereservationsystem.configs.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.moviereservationsystem.user.UserEntity;
import org.example.moviereservationsystem.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String auhHeader = request.getHeader("Authorization");
        final String userPhoneNumber;
        final String jwtToken;
        if (auhHeader == null || !auhHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        jwtToken = auhHeader.substring(7);
        userPhoneNumber = jwtToken.split(":")[0];//TODO
        if(userPhoneNumber!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserEntity user =
        }
    }
}
