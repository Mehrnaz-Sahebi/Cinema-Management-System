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
        Session session = getSession();
        try {
            Query query1 = session.createQuery("FROM CinemaEntity C WHERE C.name =: name or C.addressId =: address");
            query1.setParameter("name", cinema.getName());
            query1.setParameter("address", cinema.getAddressId());
            List results1 = query1.list();
            if (!results1.isEmpty()) {
                throw new EntityExistsException();
            }
            session.persist(cinema);
        } catch (HibernateException e) {
            LOGGER.error(LoggerMessageCreator.errorCreating("CinemaEntity", cinema.getId()), e);
            return null;
        }
        return cinema;
    }

}
