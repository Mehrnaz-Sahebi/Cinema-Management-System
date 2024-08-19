package org.example.moviereservationsystem.configs;

import lombok.RequiredArgsConstructor;
import org.example.moviereservationsystem.configs.jwt.JwtUtils;
import org.example.moviereservationsystem.user.UserEntityDetails;
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
        UserEntityDetails userEntityDetails = (UserEntityDetails) userService.loadUserByUsername(request.getPhoneNumber());
        if(userEntityDetails != null){
            return ResponseEntity.ok(jwtUtils.generateToken(userEntityDetails));
        }
        return ResponseEntity.status(400).body("Invalid phone number or password");
    }
}
