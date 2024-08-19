package org.example.moviereservationsystem.actor;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.example.moviereservationsystem.base.BaseDao;
import org.example.moviereservationsystem.cinema.CinemaEntity;
import org.example.moviereservationsystem.movie.MovieEntity;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActorDao extends BaseDao {
    public static final Logger LOGGER = LoggerFactory.getLogger(ActorDao.class);
    public ActorEntity getById(int id) throws EntityNotFoundException {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        ActorEntity actor = null;
        try {
            transaction = session.beginTransaction();
            actor = (ActorEntity) session.get(ActorEntity.class, id);
            List<MovieEntity> movies = actor.getMovies();
            Hibernate.initialize(movies);
            transaction.commit();
        } catch (HibernateException e){
            if (transaction != null) transaction.rollback();
            LOGGER.error("Error getting actor " + id, e);
        }catch (NullPointerException | EntityNotFoundException e){
            throw new EntityNotFoundException();
        }
        finally {
            session.close();
        }
        return actor;
    }
    public ActorEntity addActor(ActorEntity actor) throws EntityExistsException {
        return super.addEntity(actor);
    }
}
