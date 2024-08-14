package org.example.moviereservationsystem.movie;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieService movieService;


    @GetMapping("/movie/{id}")
    public MovieEntity getMovie(@PathVariable int id) {
        return movieService.getMovieById(id);
    }
}
