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
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        MovieEntity movie = null;
        try {
            transaction = session.beginTransaction();
            movie = (MovieEntity) session.get(MovieEntity.class, id);
            Set<ActorEntity> actors = movie.getActors();
            Hibernate.initialize(actors);
            Set<CinemaEntity> cinemas = movie.getCinemas();
            Hibernate.initialize(cinemas);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(LoggerMessageCreator.errorGetting("Movie", id), e);
            e.printStackTrace();
        } catch (NullPointerException | EntityNotFoundException e) {
            throw new EntityNotFoundException();
        } finally {
            session.close();
        }
        return movie;
    }

    public MovieEntity addMovie(MovieEntity movie) throws EntityExistsException{
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hql1 = "FROM MovieEntity M WHERE M.title =: title";
            Query query1 = session.createQuery(hql1);
            query1.setParameter("title", movie.getTitle());
            List results1 = query1.list();
            if (!results1.isEmpty()) {
                throw new EntityExistsException();
            }
            session.persist(movie);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(LoggerMessageCreator.errorCreating("Movie", movie.getTitle()), e);
            return null;
        } finally {
            session.close();
        }
        return movie;
    }
    public MovieEntity addMovieToCinema(String movieTitle, String cinemaName) throws EntityNotFoundException {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        MovieEntity movieToReturn = null;
        try {
            transaction = session.beginTransaction();

            String hql1 = "FROM MovieEntity M WHERE M.title =: title";
            Query query1 = session.createQuery(hql1);
            query1.setParameter("title", movieTitle);
            List results1 = query1.list();
            if (results1.isEmpty()) {
                throw new EntityNotFoundException("movie");
            }
            movieToReturn = (MovieEntity) results1.get(0);

            String hql2 = "FROM CinemaEntity C WHERE C.name =: cinemaName";
            Query query2 = session.createQuery(hql2);
            query2.setParameter("cinemaName",cinemaName);
            List results2 = query2.list();
            if (results2.isEmpty()){
                throw new EntityNotFoundException("cinema");
            }
            CinemaEntity cinemaToAdd = (CinemaEntity) results2.get(0);
            Set<CinemaEntity> cinemasToSave = movieToReturn.getCinemas();
            if (cinemasToSave == null){
                cinemasToSave = new HashSet<>();
            }
            cinemasToSave.add(cinemaToAdd);

            String hql3 = "UPDATE MovieEntity M SET M.cinemas =: cinemasToSave WHERE M.title =: title";
            Query query3 = session.createQuery(hql3);
            query3.setParameter("cinemasToSave",cinemasToSave);
            query3.setParameter("title",movieTitle);
            query3.executeUpdate();
            String hql4 = "FROM MovieEntity M WHERE M.title =: title";
            Query query4 = session.createQuery(hql4);
            query4.setParameter("title",movieTitle);
            List results4 = query4.getResultList();
            movieToReturn = (MovieEntity) results4.get(0);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(LoggerMessageCreator.errorUpdating("movie", movieTitle), e);
            return null;
        } finally {
            session.close();
        }
        return movieToReturn;
    }
    public MovieEntity addActorToMovie(String movieTitle, ActorEntity actor) throws EntityNotFoundException {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        MovieEntity movieToReturn = null;
        try {
            transaction = session.beginTransaction();

            String hql1 = "FROM MovieEntity M WHERE M.title =: title";
            Query query1 = session.createQuery(hql1);
            query1.setParameter("title", movieTitle);
            List results1 = query1.list();
            if (results1.isEmpty()) {
                throw new EntityNotFoundException("movie");
            }
            movieToReturn = (MovieEntity) results1.get(0);

            String hql2 = "FROM ActorEntity A WHERE A.firstName =: firstName and A.lastName =: lastName";
            Query query2 = session.createQuery(hql2);
            query2.setParameter("firstName",actor.getFirstName());
            query2.setParameter("lastName",actor.getLastName());
            List results2 = query2.list();
            if (results2.isEmpty()){
                throw new EntityNotFoundException("actor");
            }
            ActorEntity actorToAdd= (ActorEntity) results2.get(0);

            Set<ActorEntity> actorsToSave = movieToReturn.getActors();
            if (actorsToSave == null){
                actorsToSave = new HashSet<>();
            }
            actorsToSave.add(actorToAdd);

            String hql3 = "UPDATE MovieEntity M SET M.actors =: actorsToSave WHERE M.title =: title";
            Query query3 = session.createQuery(hql3);
            query3.setParameter("actorsToSave",actorsToSave);
            query3.setParameter("title",movieTitle);

            String hql4 = "FROM MovieEntity M WHERE M.title =: title";
            Query query4 = session.createQuery(hql4);
            query4.setParameter("title",movieTitle);
            List results4 = query4.getResultList();
            movieToReturn = (MovieEntity) results4.get(0);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(LoggerMessageCreator.errorUpdating("movie", movieTitle), e);
            return null;
        } finally {
            session.close();
        }
        return movieToReturn;
    }
}

