package org.example.moviereservationsystem.cinema;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.example.moviereservationsystem.base.BaseService;
import org.example.moviereservationsystem.movie.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CinemaService extends BaseService {
    @Autowired
    private CinemaDao cinemaDao;
    public CinemaEntity addCinema(CinemaEntity cinema) throws EntityExistsException {
        return cinemaDao.addCinema(cinema);
    }
    public void deleteCinema(String cinemaName) throws EntityNotFoundException {
        cinemaDao.deleteEntity("name",cinemaName,false, CinemaEntity.class);
    }
}
