package org.example.moviereservationsystem.movie;

import jakarta.persistence.EntityNotFoundException;
import org.example.moviereservationsystem.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service

public class MovieService extends BaseService {
    @Autowired
    private MovieDao movieDao;
    public MovieEntity getById(int id) throws EntityNotFoundException {
        return movieDao.getBId(id);
    }
}
