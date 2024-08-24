package org.example.moviereservationsystem.authentication;

import lombok.RequiredArgsConstructor;
import org.example.moviereservationsystem.user.UserEntityDetails;
import org.example.moviereservationsystem.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private DaoAuthenticationProvider authenticationProvider;
    public static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    @PostMapping
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest request){
        Authentication authentication = authenticationProvider.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(request.getPhoneNumber(), request.getPassword()));

        UserEntityDetails userEntityDetails = (UserEntityDetails) userService.loadUserByUsername(request.getPhoneNumber());
        if(userEntityDetails != null){
            return ResponseEntity.ok(tokenGenerator.createToken(authentication));
        }
        LOGGER.info("Authentication failed for phone number " + request.getPhoneNumber() + "and password " + request.getPassword());
        return ResponseEntity.status(400).body("Invalid phone number or password");
    }
}
