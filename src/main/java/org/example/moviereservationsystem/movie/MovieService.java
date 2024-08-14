package org.example.moviereservationsystem.movie;

import org.example.moviereservationsystem.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service

public class MovieService extends BaseService {
    @Autowired
    private MovieDao movieDao;
}
