package org.example.moviereservationsystem.admin;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
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
import org.example.moviereservationsystem.director.DirectorEntity;
import org.example.moviereservationsystem.movie.MovieDto;
import org.example.moviereservationsystem.movie.MovieEntity;
import org.example.moviereservationsystem.movie.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(RequestNames.ADMIN)
public class AdminController {
    public static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private ActorService actorService;
    @Autowired
    private MovieService movieService;

    @PostMapping("/add-cinema")
    public CinemaEntity addCinema(@RequestBody CinemaDto cinema, HttpServletResponse response) {
        CinemaEntity cinemaToReturn = null;
        AddressEntity address = new AddressEntity(cinema.getAddressId(), cinema.getNumber(), cinema.getAlley(), cinema.getStreet(), cinema.getCity());
        CinemaEntity cinemaToSave = new CinemaEntity(cinema.getName(), address, null, null);
        try {
            cinemaToReturn = cinemaService.addCinema(cinemaToSave);
        } catch (EntityExistsException e) {
            try {
                ResponseCreator.sendAlreadyExistsError(response, "cinema");
                LOGGER.info(LoggerMessageCreator.infoAlreadyExists("cinema", cinema.getName()));
            } catch (IOException ex) {
                LOGGER.error(LoggerMessageCreator.errorWritingResponse("addCinema"));
            }
        }
        return cinemaToReturn;
    }

    @PostMapping("/add-actor")
    public ActorEntity addActor(@RequestBody ActorDto actor, HttpServletResponse response) {
        ActorEntity actorToReturn = null;
        ActorEntity actorToSave = new ActorEntity(actor.getFirstName(), actor.getLastName(), null);
        try {
            actorToReturn = actorService.addActor(actorToSave);
        } catch (EntityExistsException e) {
            try {
                ResponseCreator.sendAlreadyExistsError(response, "actor");
                LOGGER.info(LoggerMessageCreator.infoAlreadyExists("actor", actor.toString()));
            } catch (IOException ex) {
                LOGGER.error(LoggerMessageCreator.errorWritingResponse("addActor"));
            }
        }
        return actorToReturn;
    }

    @PostMapping("/add-movie")
    public MovieEntity addMovie(@RequestBody MovieDto movie, HttpServletResponse response) {
        MovieEntity movieToReturn = null;
        DirectorEntity director = new DirectorEntity(movie.getDirectorFirstName(), movie.getDirectorLastName());
        MovieEntity movieToSave = new MovieEntity(movie.getTitle(), movie.getDescription(), movie.getYear(), director, movie.getGenre(), movie.getRating(), movie.getLengthInMinutes());
        try {
            movieToReturn = movieService.addMovie(movieToSave);
        } catch (EntityExistsException e) {
            try {
                ResponseCreator.sendAlreadyExistsError(response, "movie");
                LOGGER.info(LoggerMessageCreator.infoAlreadyExists("movie", movie.getTitle()));
            } catch (IOException ex) {
                LOGGER.error(LoggerMessageCreator.errorWritingResponse("addMovie"));
            }
        }
        return movieToReturn;
    }

    @PutMapping("/add-movie-to-cinema/{movieTitle}/{cinemaName}")
    public MovieEntity addMovieToCinema(@PathVariable String movieTitle, @PathVariable String cinemaName, HttpServletResponse response) {
        MovieEntity movieToRetutn = null;
        try {
            movieToRetutn = movieService.addMovieToCinema(movieTitle, cinemaName);
        } catch (EntityNotFoundException e) {
            try {
                ResponseCreator.sendNotFoundError(response, e.getMessage());
                if (e.getMessage().equals("movie"))
                    LOGGER.info(LoggerMessageCreator.infoNotFound(e.getMessage(), movieTitle));
                else LOGGER.info(LoggerMessageCreator.infoNotFound(e.getMessage(), cinemaName));
            } catch (IOException ex) {
                LOGGER.error(LoggerMessageCreator.errorWritingResponse("addMovieToCinema"));
            }
        }
        return movieToRetutn;
    }

}
