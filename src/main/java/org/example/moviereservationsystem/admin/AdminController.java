package org.example.moviereservationsystem.admin;

import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletResponse;
import org.example.moviereservationsystem.LoggerMessageCreator;
import org.example.moviereservationsystem.RequestNames;
import org.example.moviereservationsystem.ResponseCreator;
import org.example.moviereservationsystem.actor.ActorDto;
import org.example.moviereservationsystem.actor.ActorEntity;
import org.example.moviereservationsystem.actor.ActorService;
import org.example.moviereservationsystem.address.AddressEntity;
import org.example.moviereservationsystem.cinema.CinemaDto;
import org.example.moviereservationsystem.cinema.CinemaEntity;
import org.example.moviereservationsystem.cinema.CinemaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(RequestNames.ADMIN)
public class AdminController {
    public static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private ActorService actorService;
    @PostMapping("/add-cinema")
    public CinemaEntity addCinema(@RequestBody CinemaDto cinema, HttpServletResponse response) {
        CinemaEntity cinemaToReturn = null;
        AddressEntity address = new AddressEntity(cinema.getAddressId(),cinema.getNumber(),cinema.getAlley(),cinema.getStreet(),cinema.getCity());
        CinemaEntity cinemaToSave = new CinemaEntity(cinema.getName(),address,null,null);
        try {
            cinemaToReturn = cinemaService.addCinema(cinemaToSave);
        } catch (EntityExistsException e) {
            try {
                ResponseCreator.sendAlreadyExistsError(response,"cinema");
                LOGGER.info(LoggerMessageCreator.infoAlreadyExists("cinema",cinema.getName()));
            } catch (IOException ex) {
                LOGGER.error(LoggerMessageCreator.errorWritingResponse("addCinema"));
            }
        }
        return cinemaToReturn;
    }
    @PostMapping("/add-actor")
    public ActorEntity addActor(@RequestBody ActorDto actor, HttpServletResponse response) {
        ActorEntity actorToReturn = null;
        ActorEntity actorToSave = new ActorEntity(actor.getFirstName(),actor.getLastName(),null);
        try {
            actorToReturn = actorService.addActor(actorToSave);
        } catch (EntityExistsException e) {
            try {
                ResponseCreator.sendAlreadyExistsError(response,"actor");
                LOGGER.info(LoggerMessageCreator.infoAlreadyExists("actor",actor.toString()));
            } catch (IOException ex) {
                LOGGER.error(LoggerMessageCreator.errorWritingResponse("addActor"));
            }
        }
        return actorToReturn;
    }
}
