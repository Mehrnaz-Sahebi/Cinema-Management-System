package org.example.moviereservationsystem.cinema;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.Query;
import org.example.moviereservationsystem.MessageCreator;
import org.example.moviereservationsystem.base.BaseDao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CinemaDao extends BaseDao {
    public CinemaEntity addCinema(CinemaEntity cinema) throws EntityExistsException {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hql = "select C.name FROM CinemaEntity C WHERE C.name =: name";
            Query query = session.createQuery(hql);
            query.setParameter("name",cinema.getName());
            List results = query.getResultList();
            if (results.size() > 0) {
                throw new EntityExistsException();
            }
            //TODO replace save
            session.persist(cinema);
            transaction.commit();
        }
        catch (HibernateException e){
            if (transaction != null) transaction.rollback();
            LOGGER.error(MessageCreator.errorCreating("Cinema",cinema.getId()), e);
            return null;
        }
        finally{
            session.close();
        }
        return cinema;
    }
}
