package org.example.moviereservationsystem.movie;

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
}
