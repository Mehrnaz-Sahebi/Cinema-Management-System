package org.example.moviereservationsystem.main_controllers;

import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletResponse;
import org.example.moviereservationsystem.LoggerMessageCreator;
import org.example.moviereservationsystem.ResponseCreator;
import org.example.moviereservationsystem.address.AddressEntity;
import org.example.moviereservationsystem.cinema.CinemaEntity;
import org.example.moviereservationsystem.user.UserDto;
import org.example.moviereservationsystem.user.UserEntity;
import org.example.moviereservationsystem.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CustomerController {
    public static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private UserService userService;
    @PostMapping("/sign-up")
    public UserEntity signUp(@RequestBody UserDto user, HttpServletResponse response) {
        UserEntity userToReturn = null;
        UserEntity userToSignUp = new UserEntity(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),user.getPassword(),"CUSTOMER");
        try {
            userToReturn = userService.addUser(userToSignUp);
        } catch (EntityExistsException e) {
            try {
                ResponseCreator.sendAlreadyExistsError(response, "user");
                LOGGER.info(LoggerMessageCreator.infoAlreadyExists("UserEntity", user.getId()));
            } catch (IOException ex) {
                LOGGER.error(LoggerMessageCreator.errorWritingResponse("signUp"));
            }
        }
        return userToReturn;
    }
}
