package org.example.moviereservationsystem.actor;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.example.moviereservationsystem.LoggerMessageCreator;
import org.example.moviereservationsystem.base.BaseDao;
import org.example.moviereservationsystem.movie.MovieEntity;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

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
            Set<MovieEntity> movies = actor.getMovies();
            Hibernate.initialize(movies);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(LoggerMessageCreator.errorGetting("Actor", id), e);
        } catch (NullPointerException | EntityNotFoundException e) {
            throw new EntityNotFoundException();
        } finally {
            session.close();
        }
        return actor;
    }

    public ActorEntity addActor(ActorEntity actor) throws EntityExistsException {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hql1 = "FROM ActorEntity A WHERE A.firstName =: firstName and A.lastName =: lastName";
            Query query1 = session.createQuery(hql1);
            query1.setParameter("lastName", actor.getLastName());
            query1.setParameter("firstName", actor.getFirstName());
            List results1 = query1.list();
            if (!results1.isEmpty()) {
                throw new EntityExistsException();
            }
            session.persist(actor);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(LoggerMessageCreator.errorCreating("Actor", actor.toString()), e);
            return null;
        } finally {
            session.close();
        }
        return actor;
    }
}

