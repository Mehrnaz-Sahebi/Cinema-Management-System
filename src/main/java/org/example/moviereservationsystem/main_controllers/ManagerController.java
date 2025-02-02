package org.example.moviereservationsystem.main_controllers;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.example.moviereservationsystem.LoggerMessageCreator;
import org.example.moviereservationsystem.RequestNames;
import org.example.moviereservationsystem.ResponseCreator;
import org.example.moviereservationsystem.auditorium.AuditoriumDto;
import org.example.moviereservationsystem.auditorium.AuditoriumEntity;
import org.example.moviereservationsystem.auditorium.AuditoriumService;
import org.example.moviereservationsystem.movie.MovieEntity;
import org.example.moviereservationsystem.movie.MovieService;
import org.example.moviereservationsystem.schedule.ScheduleDto;
import org.example.moviereservationsystem.schedule.ScheduleEntity;
import org.example.moviereservationsystem.schedule.ScheduleException;
import org.example.moviereservationsystem.schedule.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(RequestNames.MANAGER)
public class ManagerController {
    public static final Logger LOGGER = LoggerFactory.getLogger(ManagerController.class);
    @Autowired
    private MovieService movieService;
    @Autowired
    private AuditoriumService auditoriumService;
    @Autowired
    private ScheduleService scheduleService;

    @PutMapping("/add-movie-to-cinema/{movieTitle}/{cinemaName}")
    public MovieEntity addMovieToCinema(@PathVariable String movieTitle, @PathVariable String cinemaName, HttpServletResponse response) {
        MovieEntity movieToRetutn = null;
        try {
            movieToRetutn = movieService.addMovieToCinema(movieTitle, cinemaName);
        } catch (EntityNotFoundException e) {
            try {
                ResponseCreator.sendNotFoundError(response, e.getMessage());
                if (e.getMessage().equals("movie"))
                    LOGGER.info(LoggerMessageCreator.infoNotFound("MovieEntity", movieTitle));
                else LOGGER.info(LoggerMessageCreator.infoNotFound("CinemaEntity", cinemaName));
            } catch (IOException ex) {
                LOGGER.error(LoggerMessageCreator.errorWritingResponse("addMovieToCinema"));
            }
        }
        return movieToRetutn;
    }

    @PostMapping("/add-auditorium")
    public AuditoriumEntity addAuditorium(@RequestBody AuditoriumDto auditorium, HttpServletResponse response) {
        AuditoriumEntity auditoriumToReturn = null;
        AuditoriumEntity auditoriumToSave = new AuditoriumEntity(auditorium.getName(), auditorium.getCapacity(), auditorium.getRowCount());
        try {
            auditoriumToReturn = auditoriumService.addAuditorium(auditoriumToSave);
        } catch (EntityExistsException e) {
            try {
                ResponseCreator.sendAlreadyExistsError(response, "Auditorium");
                LOGGER.info(LoggerMessageCreator.infoAlreadyExists("Auditorium", auditorium.getName()));
            } catch (IOException ex) {
                LOGGER.error(LoggerMessageCreator.errorWritingResponse("addAuditorium"));
            }
        }
        return auditoriumToReturn;
    }

    @PutMapping("/add-auditorium-to-cinema/{auditoriumName}/{cinemaName}")
    public AuditoriumEntity addAuditoriumToCinema(@PathVariable String auditoriumName, @PathVariable String cinemaName, HttpServletResponse response) {
        AuditoriumEntity auditoriumToReturn = null;
        try {
            auditoriumToReturn = auditoriumService.addAuditoriumToCinema(auditoriumName, cinemaName);
        } catch (EntityNotFoundException e) {
            try {
                ResponseCreator.sendNotFoundError(response, e.getMessage());
                if (e.getMessage().equals("auditorium"))
                    LOGGER.info(LoggerMessageCreator.infoNotFound("AuditoriumEntity", auditoriumName));
                else LOGGER.info(LoggerMessageCreator.infoNotFound("CinemaEntity", cinemaName));
            } catch (IOException ex) {
                LOGGER.error(LoggerMessageCreator.errorWritingResponse("addMovieToCinema"));
            }
        }
        return auditoriumToReturn;
    }

    @PostMapping("/add-schedule")
    public ScheduleEntity addSchedule(@RequestBody ScheduleDto schedule, HttpServletResponse response) {
        ScheduleEntity scheduleToReturn = null;
        ScheduleEntity scheduleToSave = new ScheduleEntity(schedule.getEndingTime(), schedule.getStartingTime(), schedule.getMovie(), schedule.getAuditorium(), schedule.getPrice());
        scheduleToSave.setRemainingTicketCount(schedule.getAuditorium().getCapacity());
        try {
            scheduleToReturn = scheduleService.addSchedule(scheduleToSave);
        } catch (EntityNotFoundException e) {
            try {
                ResponseCreator.sendNotFoundError(response, e.getMessage());
                if (e.getMessage().equals("auditorium"))
                    LOGGER.info(LoggerMessageCreator.infoNotFound("AuditoriumEntity", schedule.getAuditorium().getName()));
                else LOGGER.info(LoggerMessageCreator.infoNotFound("MovieEntity", schedule.getMovie().getTitle()));
            } catch (IOException ex) {
                LOGGER.error(LoggerMessageCreator.errorWritingResponse("addSchedule"));
            }
        } catch (ScheduleException e) {
            try {
                ResponseCreator.sendScheduleConflictError(response, e.getMessage());
                LOGGER.info(LoggerMessageCreator.infoScheduleConflict(e.getMessage(), scheduleToSave.toString()));

            } catch (IOException ex) {
                LOGGER.error(LoggerMessageCreator.errorWritingResponse("addSchedule"));
            }
        }
        return scheduleToReturn;
    }
}
