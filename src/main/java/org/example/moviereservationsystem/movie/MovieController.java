package org.example.moviereservationsystem.movie;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.example.moviereservationsystem.RequestNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RequestNames.MOVIES)
public class MovieController {
    public static final String SLASH_MOVIE_SLASH = "/movie/";
    @Autowired
    private MovieService movieService;


    @GetMapping(SLASH_MOVIE_SLASH+"{id}")
    public MovieEntity getMovie(@PathVariable int id, HttpServletResponse response) {
        MovieEntity movie = null;
        try {
            movie = movieService.getById(id,MovieEntity.class);
        } catch (EntityNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return movie;
    }
    @PostMapping(SLASH_MOVIE_SLASH+"{movie}")
    public MovieEntity addMovie(@PathVariable MovieEntity movie, HttpServletResponse response) {
        MovieEntity movieToReturn = null;
        try {
            movieToReturn = movieService.addEntity(movie);
        }catch (EntityExistsException e){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } return movieToReturn;
    }
}
