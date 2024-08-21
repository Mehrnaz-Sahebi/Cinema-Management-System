package org.example.moviereservationsystem.auditorium;

import jakarta.persistence.EntityExistsException;
import org.example.moviereservationsystem.LoggerMessageCreator;
import org.example.moviereservationsystem.base.BaseDao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuditoriumDao extends BaseDao {
    public AuditoriumEntity auditorium(AuditoriumEntity auditorium) throws EntityExistsException {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hql1 = "FROM AuditoriumEntity A WHERE A.name =: name";
            Query query1 = session.createQuery(hql1);
            query1.setParameter("name", auditorium.getName());
            List results1 = query1.list();
            if (!results1.isEmpty()) {
                throw new EntityExistsException();
            }
            session.persist(auditorium);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(LoggerMessageCreator.errorCreating("Auditorium", auditorium.getName()), e);
            return null;
        } finally {
            session.close();
        }
        return auditorium;
    }
}
