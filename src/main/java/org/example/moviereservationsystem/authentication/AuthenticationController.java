package org.example.moviereservationsystem.authentication;

import lombok.RequiredArgsConstructor;
import org.example.moviereservationsystem.authentication.jwt.JwtUtils;
import org.example.moviereservationsystem.user.UserEntityDetails;
import org.example.moviereservationsystem.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @PostMapping
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getPhoneNumber(),request.getPassword()));
        UserEntityDetails userEntityDetails = (UserEntityDetails) userService.loadUserByUsername(request.getPhoneNumber());
        if(userEntityDetails != null){
            return ResponseEntity.ok(jwtUtils.generateToken(userEntityDetails));
        }
        return ResponseEntity.status(400).body("Invalid phone number or password");
    }
}
