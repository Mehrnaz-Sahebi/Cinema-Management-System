package org.example.moviereservationsystem.main_controllers;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.moviereservationsystem.LoggerMessageCreator;
import org.example.moviereservationsystem.ResponseCreator;
import org.example.moviereservationsystem.schedule.ScheduleEntity;
import org.example.moviereservationsystem.schedule.ScheduleService;
import org.example.moviereservationsystem.ticket.TicketEntity;
import org.example.moviereservationsystem.ticket.TicketException;
import org.example.moviereservationsystem.ticket.TicketService;
import org.example.moviereservationsystem.user.UserDto;
import org.example.moviereservationsystem.user.UserEntity;
import org.example.moviereservationsystem.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
public class CustomerController {
    public static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private JwtDecoder jwtDecoder;
    @Autowired
    private TicketService ticketService;

    @PostMapping("/sign-up")
    public UserEntity signUp(@RequestBody UserDto user, HttpServletResponse response) {
        UserEntity userToReturn = null;
        UserEntity userToSignUp = new UserEntity(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), "CUSTOMER");
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
                ResponseCreator.sendNotFoundError(response, "movie");
                LOGGER.info((LoggerMessageCreator.infoNotFound("MovieEntity", movieTitle)));
            } catch (IOException ex) {
                LOGGER.error(LoggerMessageCreator.errorWritingResponse("getSchedulesForMovie"));
            }
        }
        return schedules;
    }

    @GetMapping("/schedules-for-cinema/{cinemaName}")
    public List<ScheduleEntity> getSchedulesForCinema(@PathVariable String cinemaName, HttpServletResponse response) {
        List<ScheduleEntity> schedules = null;
        try {
            schedules = scheduleService.getSchedulesForCinema(cinemaName);
        } catch (EntityNotFoundException e) {
            try {
                ResponseCreator.sendNotFoundError(response, "cinema");
                LOGGER.info((LoggerMessageCreator.infoNotFound("CinemaEntity", cinemaName)));
            } catch (IOException ex) {
                LOGGER.error(LoggerMessageCreator.errorWritingResponse("getSchedulesForCinema"));
            }
        }
        return schedules;
    }

    @GetMapping("/schedules-for-date")
    public List<ScheduleEntity> getSchedulesForDate(@RequestBody Date date, HttpServletResponse response) {
        List<ScheduleEntity> schedules = scheduleService.getSchedulesForDate(date);
        return schedules;
    }

    @PostMapping("/reserve-ticket/{scheduleId}")
    public TicketEntity reserveTicket(HttpServletRequest request, @PathVariable int scheduleId, HttpServletResponse response) {
        TicketEntity ticket = null;
        try {
            ticket = ticketService.reserveTicket(scheduleId, getPhoneNumber(request));
        } catch (EntityNotFoundException e) {
            try {
                ResponseCreator.sendNotFoundError(response, e.getMessage());
                if (e.getMessage().equals("user"))
                    LOGGER.info(LoggerMessageCreator.infoNotFound("UserEntity", getPhoneNumber(request)));
                else LOGGER.info(LoggerMessageCreator.infoNotFound("ScheduleEntity", scheduleId));
            } catch (IOException ex) {
                LOGGER.error(LoggerMessageCreator.errorWritingResponse("reserveTicket"));
            }
        } catch (TicketException e) {
            try {
                LOGGER.info(LoggerMessageCreator.infoTicketReservationFailed(e.getMessage(), scheduleId, getPhoneNumber(request)));
                ResponseCreator.sendTicketReservationError(response, e.getMessage());
            } catch (IOException exception) {
                LOGGER.error(LoggerMessageCreator.errorWritingResponse("reserveTicket"));
            }
        }
        return ticket;
    }

    @GetMapping("/my-tickets")
    public List<TicketEntity> getMyTickets(HttpServletRequest request, HttpServletResponse response) {
        List<TicketEntity> tickets = null;
        tickets = ticketService.getMyTickets(getPhoneNumber(request));
        return tickets;
    }

    @DeleteMapping("/cancel-ticket/{ticketId}")
    public void cancelTicket(HttpServletRequest request, @PathVariable int ticketId, HttpServletResponse response) {
        try {
            ticketService.cancelTicket(getPhoneNumber(request), ticketId);
        } catch (EntityNotFoundException e) {
            try {
                LOGGER.info(LoggerMessageCreator.infoNotFound("TicketEntity", ticketId));
                ResponseCreator.sendNotFoundError(response, "ticket");
            } catch (IOException exception) {
                LOGGER.error(LoggerMessageCreator.errorWritingResponse("cancelTicket"));
            }
        } catch (TicketException e) {
            try {
                LOGGER.info(LoggerMessageCreator.infoTicketCancellationFailed(e.getMessage(), ticketId));
                ResponseCreator.sendTicketCancellationError(response, e.getMessage());
            } catch (IOException exception) {
                LOGGER.error(LoggerMessageCreator.errorWritingResponse("cancelTicket"));
            }
        }
    }
    @PutMapping("/charge-account/{amount}")
    public UserEntity chargeAccount(HttpServletRequest request,@PathVariable long amount, HttpServletResponse response) {
        UserEntity userToReturn = null;
        try {
            userToReturn = userService.chargeAccount(getPhoneNumber(request), amount);
        } catch (EntityNotFoundException e) {
            try {
                LOGGER.info(LoggerMessageCreator.infoNotFound("UserEntity", getPhoneNumber(request)));
                ResponseCreator.sendNotFoundError(response,"user");
            } catch (IOException exception) {
                LOGGER.error(LoggerMessageCreator.errorWritingResponse("chargeAccount"));
            }
        }
        return userToReturn;
    }

    public int getPhoneNumber(HttpServletRequest request) {
//        String authHeader = request.getHeader("Authorization");
//        String jwtToken = authHeader.substring(7);
//        Jwt jwt = jwtDecoder.decode(jwtToken);
//        String userPhoneNumber = jwt.getSubject();
//        System.out.println(userPhoneNumber);
//        return Integer.parseInt(userPhoneNumber);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Integer.parseInt(authentication.getName());
    }
}
