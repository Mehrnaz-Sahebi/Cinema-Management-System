package org.example.moviereservationsystem.movie;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieService movieService;


    @GetMapping("/movie/{id}")
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
