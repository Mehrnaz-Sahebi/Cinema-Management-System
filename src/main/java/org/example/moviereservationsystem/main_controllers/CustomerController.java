package org.example.moviereservationsystem.main_controllers;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.example.moviereservationsystem.LoggerMessageCreator;
import org.example.moviereservationsystem.ResponseCreator;
import org.example.moviereservationsystem.address.AddressEntity;
import org.example.moviereservationsystem.cinema.CinemaEntity;
import org.example.moviereservationsystem.schedule.ScheduleEntity;
import org.example.moviereservationsystem.schedule.ScheduleService;
import org.example.moviereservationsystem.user.UserDto;
import org.example.moviereservationsystem.user.UserEntity;
import org.example.moviereservationsystem.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
public class CustomerController {
    public static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private ScheduleService scheduleService;
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
    @GetMapping("/all-schedules")
    public List<ScheduleEntity> getAllSchedules(HttpServletResponse response) {
        return scheduleService.getAllSchedules();
    }
    @GetMapping("/schedules-for-movie/{movieTitle}")
    public List<ScheduleEntity> getSchedulesForMovie(@PathVariable String movieTitle, HttpServletResponse response) {
        List<ScheduleEntity> schedules = null;
        try {
            schedules = scheduleService.getSchedulesForMovie(movieTitle);
        } catch (EntityNotFoundException e) {
            try {
                ResponseCreator.sendNotFoundError(response,"movie");
                LOGGER.info((LoggerMessageCreator.infoNotFound("MovieEntity", movieTitle)));
            } catch (IOException ex) {
                LOGGER.error(LoggerMessageCreator.errorWritingResponse("getSchedulesForMovie"));
            }
        }
        return schedules;
    }
}
