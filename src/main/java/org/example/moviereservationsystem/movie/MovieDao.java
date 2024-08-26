package org.example.moviereservationsystem.movie;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.example.moviereservationsystem.LoggerMessageCreator;
import org.example.moviereservationsystem.base.BaseDao;
import org.example.moviereservationsystem.actor.ActorEntity;
import org.example.moviereservationsystem.cinema.CinemaEntity;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class MovieDao extends BaseDao {
    public static final Logger LOGGER = LoggerFactory.getLogger(MovieDao.class);

    public MovieEntity getById(int id) throws EntityNotFoundException {
        Session session = getSession();
        MovieEntity movie = null;
        movie = (MovieEntity) session.get(MovieEntity.class, id);
        return movie;
    }

    public MovieEntity addMovie(MovieEntity movie) throws EntityExistsException {
        Session session = getSession();
        Query query1 = session.createQuery("FROM MovieEntity M WHERE M.title =: title");
        query1.setParameter("title", movie.getTitle());
        List results1 = query1.list();
        if (!results1.isEmpty()) {
            throw new EntityExistsException();
        }
        session.persist(movie);
        return movie;
    }

    public MovieEntity addMovieToCinema(String movieTitle, String cinemaName) throws EntityNotFoundException {
        Session session = getSession();
        MovieEntity movieToReturn = null;
        Query query1 = session.createQuery("FROM MovieEntity M WHERE M.title =: title");
        query1.setParameter("title", movieTitle);
        List results1 = query1.list();
        if (results1.isEmpty()) {
            throw new EntityNotFoundException("movie");
        }
        movieToReturn = (MovieEntity) results1.get(0);

        Query query2 = session.createQuery("FROM CinemaEntity C WHERE C.name =: cinemaName");
        query2.setParameter("cinemaName", cinemaName);
        List results2 = query2.list();
        if (results2.isEmpty()) {
            throw new EntityNotFoundException("cinema");
        }
        CinemaEntity cinemaToAdd = (CinemaEntity) results2.get(0);
        Set<CinemaEntity> cinemasToSave = movieToReturn.getCinemas();
        if (cinemasToSave == null) {
            cinemasToSave = new HashSet<>();
        }
        cinemasToSave.add(cinemaToAdd);
        movieToReturn.setCinemas(cinemasToSave);
        return movieToReturn;
    }

    public MovieEntity addActorToMovie(String movieTitle, ActorEntity actor) throws EntityNotFoundException {
        Session session = getSession();
        MovieEntity movieToReturn = null;

        Query query1 = session.createQuery("FROM MovieEntity M WHERE M.title =: title");
        query1.setParameter("title", movieTitle);
        List results1 = query1.list();
        if (results1.isEmpty()) {
            throw new EntityNotFoundException("movie");
        }
        movieToReturn = (MovieEntity) results1.get(0);

        Query query2 = session.createQuery("FROM ActorEntity A WHERE A.firstName =: firstName and A.lastName =: lastName");
        query2.setParameter("firstName", actor.getFirstName());
        query2.setParameter("lastName", actor.getLastName());
        List results2 = query2.list();
        if (results2.isEmpty()) {
            throw new EntityNotFoundException("actor");
        }
        ActorEntity actorToAdd = (ActorEntity) results2.get(0);

        Set<ActorEntity> actorsToSave = movieToReturn.getActors();
        if (actorsToSave == null) {
            actorsToSave = new HashSet<>();
        }
        actorsToSave.add(actorToAdd);
        movieToReturn.setActors(actorsToSave);
        return movieToReturn;
    }
}

