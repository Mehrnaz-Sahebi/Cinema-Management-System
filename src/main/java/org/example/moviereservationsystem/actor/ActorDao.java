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

import java.util.List;

public class ActorDao extends BaseDao {
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
            e.printStackTrace();
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
