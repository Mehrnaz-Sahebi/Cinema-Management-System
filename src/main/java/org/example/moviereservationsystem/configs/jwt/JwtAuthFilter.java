package org.example.moviereservationsystem.configs.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.moviereservationsystem.user.UserEntityDetails;
import org.example.moviereservationsystem.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    @Getter
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

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
        userPhoneNumber = jwtUtils.extractUsername(jwtToken);
        if(userPhoneNumber!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserEntityDetails userEntityDetails = (UserEntityDetails) userService.loadUserByUsername(userPhoneNumber);
            if (jwtUtils.isTokenValid(jwtToken, userEntityDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userEntityDetails,null, userEntityDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
