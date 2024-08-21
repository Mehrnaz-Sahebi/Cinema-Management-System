package org.example.moviereservationsystem.cinema;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.example.moviereservationsystem.LoggerMessageCreator;
import org.example.moviereservationsystem.base.BaseDao;
import org.example.moviereservationsystem.movie.MovieEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class CinemaDao extends BaseDao {
    public static final Logger LOGGER = LoggerFactory.getLogger(CinemaDao.class);
    public CinemaEntity addCinema(CinemaEntity cinema) throws EntityExistsException {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hql1 = "FROM CinemaEntity C WHERE C.name =: name";
            Query query1 = session.createQuery(hql1);
            query1.setParameter("name",cinema.getName());
            List results1 = query1.list();
            if (!results1.isEmpty()) {
                throw new EntityExistsException();
            }
            String hql2 = "FROM CinemaEntity C WHERE C.addressId =: address";
            Query query2 = session.createQuery(hql2);
            query2.setParameter("address",cinema.getAddressId());
            List results2 = query2.list();
            if (!results2.isEmpty()){
                throw new EntityExistsException();
            }
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
