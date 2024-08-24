package org.example.moviereservationsystem.movie;

import jakarta.persistence.Access;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.example.moviereservationsystem.actor.ActorEntity;
import org.example.moviereservationsystem.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class MovieService extends BaseService {
    @Autowired
    private MovieDao movieDao;

    public MovieEntity getById(int id) throws EntityNotFoundException {
        return movieDao.getById(id);
    }

    public MovieEntity addMovie(MovieEntity movie) throws EntityExistsException {
        return movieDao.addMovie(movie);
    }

    public MovieEntity addMovieToCinema(String movieTitle, String cinemaName) throws EntityNotFoundException {
        return movieDao.addMovieToCinema(movieTitle, cinemaName);
    }

    public MovieEntity addActorToMovie(String movieTitle, ActorEntity actor) throws EntityNotFoundException {
        return movieDao.addActorToMovie(movieTitle, actor);
    }

    public void deleteMovie(String movieTitle) throws EntityNotFoundException {
        movieDao.deleteEntity("title", movieTitle, false, MovieEntity.class);
    }
}
