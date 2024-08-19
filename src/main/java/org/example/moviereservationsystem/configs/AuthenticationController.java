package org.example.moviereservationsystem.configs;

import lombok.RequiredArgsConstructor;
import org.example.moviereservationsystem.configs.jwt.JwtUtils;
import org.example.moviereservationsystem.user.UserEntityPrincipal;
import org.example.moviereservationsystem.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtUtils jwtUtils;
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getPhoneNumber(),request.getPassword()));
        UserEntityPrincipal userEntityPrincipal = (UserEntityPrincipal) userService.loadUserByUsername(request.getPhoneNumber());
        if(userEntityPrincipal != null){
            return ResponseEntity.ok(jwtUtils.generateToken(userEntityPrincipal));
        }
        return ResponseEntity.status(400).body("Invalid phone number or password");
    }
}
