package org.example.moviereservationsystem.director;

import jakarta.persistence.EntityNotFoundException;
import org.example.moviereservationsystem.base.BaseDao;
import org.example.moviereservationsystem.movie.MovieEntity;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DirectorDao extends BaseDao {
    public final static Logger LOGGER = LoggerFactory.getLogger(DirectorDao.class);
    public DirectorEntity getById(int id) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        DirectorEntity director = null;
        try {
            transaction = session.beginTransaction();
            director = (DirectorEntity) session.get(DirectorEntity.class, id);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error("Error getting director "+director.getDirectorId(),e);
        } catch (NullPointerException e) {
            throw new EntityNotFoundException();
        } finally {
            session.close();
        }
        if (director == null) throw new EntityNotFoundException();
        return director;
    }
}
