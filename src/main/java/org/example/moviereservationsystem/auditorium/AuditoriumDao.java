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
    public AuditoriumEntity addAuditorium(AuditoriumEntity auditorium) throws EntityExistsException {
        Session session = getSession();
        Query query1 = session.createQuery("FROM AuditoriumEntity A WHERE A.name =: name");
        query1.setParameter("name", auditorium.getName());
        List results1 = query1.list();
        if (!results1.isEmpty()) {
            throw new EntityExistsException();
        }
        session.persist(auditorium);
        return auditorium;
    }

    public AuditoriumEntity addAuditoriumToCinema(String auditoriumName, String cinemaName) throws EntityNotFoundException {
        Session session = getSession();
        AuditoriumEntity auditoriumToReturn = null;
        Query query1 = session.createQuery("FROM CinemaEntity C WHERE C.name =: name");
        query1.setParameter("name", cinemaName);
        List results1 = query1.list();
        if (results1.isEmpty()) {
            throw new EntityNotFoundException("cinema");
        }
        CinemaEntity cinemaToSave = (CinemaEntity) results1.get(0);
        Query query2 = session.createQuery("FROM AuditoriumEntity A WHERE A.name =: name");
        query2.setParameter("name", auditoriumName);
        List results2 = query2.list();
        if (results2.isEmpty()) {
            throw new EntityNotFoundException("auditorium");
        }
        auditoriumToReturn = (AuditoriumEntity) results2.get(0);
        auditoriumToReturn.setCinema(cinemaToSave);
        return auditoriumToReturn;
    }
}
