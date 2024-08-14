package org.example.moviereservationsystem.movie;

import org.example.moviereservationsystem.Dao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class MovieDao extends Dao {
    public MovieEntity getMovieById(int id) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        MovieEntity movie = null;
        try {
            transaction = session.beginTransaction();
            movie = (MovieEntity) session.get(MovieEntity.class, id);
            transaction.commit();
        } catch (HibernateException e){
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        if (movie==null) return new MovieEntity();
        return movie;
    }
}
