package org.example.moviereservationsystem.authentication.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.moviereservationsystem.user.UserEntityDetails;
import org.example.moviereservationsystem.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthFilter.class);
    //TODO doesn't accept requests from browser
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String userPhoneNumber;
        final String jwtToken;
        if (authHeader == null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        jwtToken = authHeader.substring(7);
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
