package org.example.moviereservationsystem.movie;

import org.example.moviereservationsystem.Dao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class MovieDao extends Dao {
    public MovieEntity getMovieById(int id) {
        MovieEntity movie = super.getById(id, MovieEntity.class);
        if (movie == null) return new MovieEntity();
        return movie;
    }
}
