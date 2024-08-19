package org.example.moviereservationsystem.movie;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.example.moviereservationsystem.RequestNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RequestNames.MOVIES)
public class MovieController {
    public static final String SLASH_MOVIE_SLASH = "/movie/";
    public static final String SLASH_ADD_MOVIE = "/add-movie";
    @Autowired
    private MovieService movieService;
    public static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

    @GetMapping(SLASH_MOVIE_SLASH+"{id}")
    public MovieEntity getMovie(@PathVariable int id, HttpServletResponse response) {
        MovieEntity movie = null;
        try {
            movie = movieService.getById(id);
        } catch (EntityNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.info("Movie "+id+" not found");
        }
        return movie;
    }
    @PostMapping(SLASH_ADD_MOVIE)
    public MovieEntity addMovie(@RequestBody MovieEntity movie, HttpServletResponse response) {
        MovieEntity movieToReturn = null;
        try {
            movieToReturn = movieService.addMovie(movie);
        }catch (EntityExistsException e){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            LOGGER.info("Movie "+movie.getId()+" already exists");
        } return movieToReturn;
    }
}
