package org.example.moviereservationsystem.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service

public class MovieService {
    @Autowired
    private MovieDao movieDao;
}
