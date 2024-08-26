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
        Session session = getSession();
        ActorEntity actor = null;
        try {
            actor = (ActorEntity) session.get(ActorEntity.class, id);
            if (actor == null) {
                throw new EntityNotFoundException();
            }
        }catch (HibernateException e) {
            LOGGER.error(LoggerMessageCreator.errorGetting("ActorEntity",id),e);
        }
        return actor;
    }

    public ActorEntity addActor(ActorEntity actor) throws EntityExistsException {
        Session session = getSession();
        try {
            Query query1 = session.createQuery("FROM ActorEntity A WHERE A.firstName =: firstName and A.lastName =: lastName");
            query1.setParameter("lastName", actor.getLastName());
            query1.setParameter("firstName", actor.getFirstName());
            List results1 = query1.list();
            if (!results1.isEmpty()) {
                throw new EntityExistsException();
            }
            session.persist(actor);
        } catch (HibernateException e) {
            LOGGER.error(LoggerMessageCreator.errorCreating("ActorEntity", actor.toString()), e);
            return null;
        }
        return actor;
    }

    public void deleteActor(String firstName, String lastName) throws EntityNotFoundException {
        Session session = getSession();
        try {
            Query query1 = session.createQuery("FROM ActorEntity A WHERE A.firstName =: firstName and A.lastName =: lastName");
            query1.setParameter("firstName", firstName);
            query1.setParameter("lastName", lastName);
            List results1 = query1.list();
            if (results1.isEmpty()) {
                throw new EntityNotFoundException();
            }
            Query query = session.createQuery("delete from ActorEntity where firstName =: firstName and lastName =: lastName");
            query.setParameter("firstName", firstName);
            query.setParameter("lastName", lastName);
            query.executeUpdate();
        } catch (HibernateException e) {
            LOGGER.error(LoggerMessageCreator.errorDeleting("ActorEntity", firstName + " " + lastName), e);
        }
    }
}

