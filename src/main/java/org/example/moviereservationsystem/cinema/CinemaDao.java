package org.example.moviereservationsystem.cinema;

import jakarta.persistence.EntityExistsException;
import org.example.moviereservationsystem.LoggerMessageCreator;
import org.example.moviereservationsystem.base.BaseDao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CinemaDao extends BaseDao {
    public static final Logger LOGGER = LoggerFactory.getLogger(CinemaDao.class);
    public CinemaEntity addCinema(CinemaEntity cinema) throws EntityExistsException {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hql = "FROM CinemaEntity C WHERE C.name =: name";
            Query query = session.createQuery(hql);
            query.setParameter("name",cinema.getName());
            List results = query.list();
            if (results.size() > 0) {
                throw new EntityExistsException();
            }
            //TODO replace save
            session.persist(cinema);
            transaction.commit();
        }
        catch (HibernateException e){
            if (transaction != null) transaction.rollback();
            LOGGER.error(LoggerMessageCreator.errorCreating("Cinema",cinema.getId()), e);
            return null;
        }
        finally{
            session.close();
        }
        return cinema;
    }
}
