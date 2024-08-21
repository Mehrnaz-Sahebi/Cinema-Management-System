package org.example.moviereservationsystem.auditorium;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.example.moviereservationsystem.LoggerMessageCreator;
import org.example.moviereservationsystem.base.BaseDao;
import org.example.moviereservationsystem.cinema.CinemaEntity;
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

    public AuditoriumEntity addAuditoriumToCinema(String auditoriumName, String cinemaName) throws EntityNotFoundException {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        AuditoriumEntity auditoriumToReturn = null;
        try {
            transaction = session.beginTransaction();

            String hql1 = "FROM CinemaEntity C WHERE C.name =: name";
            Query query1 = session.createQuery(hql1);
            query1.setParameter("name", cinemaName);
            List results1 = query1.list();
            if (results1.isEmpty()) {
                throw new EntityNotFoundException("cinema");
            }
            CinemaEntity cinemaToSave = (CinemaEntity) results1.get(0);


            String hql2 = "FROM AuditoriumEntity A WHERE A.name =: name";
            Query query2 = session.createQuery(hql2);
            query2.setParameter("name", auditoriumName);
            List results2 = query2.list();
            if (results2.isEmpty()) {
                throw new EntityNotFoundException("auditorium");
            }

            String hql3 = "UPDATE AuditoriumEntity A SET A.cinema =: cinemaToSave WHERE A.name =: name";
            Query query3 = session.createQuery(hql3);
            query3.setParameter("cinemaToSave", cinemaToSave);
            query3.setParameter("name", auditoriumName);
            query3.executeUpdate();

            String hql4 = "FROM AuditoriumEntity A WHERE A.name =: name";
            Query query4 = session.createQuery(hql4);
            query4.setParameter("name", auditoriumName);
            List results4 = query4.list();
            auditoriumToReturn = (AuditoriumEntity) results4.get(0);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(LoggerMessageCreator.errorUpdating("Auditorium", auditoriumName), e);
            return null;
        } finally {
            session.close();
        }
        return auditoriumToReturn;
    }
}
