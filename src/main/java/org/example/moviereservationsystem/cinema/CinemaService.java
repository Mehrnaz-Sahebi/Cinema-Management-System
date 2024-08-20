package org.example.moviereservationsystem.cinema;

import jakarta.persistence.EntityExistsException;
import org.example.moviereservationsystem.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CinemaService extends BaseService {
    @Autowired
    private CinemaDao cinemaDao;
    public CinemaEntity addCinema(CinemaEntity cinema) throws EntityExistsException {
        return cinemaDao.addCinema(cinema);
    }
}
