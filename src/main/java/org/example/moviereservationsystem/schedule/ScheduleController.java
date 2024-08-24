package org.example.moviereservationsystem.schedule;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.example.moviereservationsystem.LoggerMessageCreator;
import org.example.moviereservationsystem.RequestNames;
import org.example.moviereservationsystem.user.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RequestNames.SCHEDULE)
public class ScheduleController {
    public static final Logger LOGGER = LoggerFactory.getLogger(ScheduleController.class);
    @Autowired
    private ScheduleService scheduleService;
    @GetMapping("/get-schedule/{id}")
    public ScheduleEntity getUser(@PathVariable int id, HttpServletResponse response) {
        ScheduleEntity schedule = null;
        try {
            schedule = scheduleService.getSchedule(id);
        } catch (EntityNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.info(LoggerMessageCreator.infoNotFound("ScheduleEntity", id));
        }
        return schedule;
    }
}
