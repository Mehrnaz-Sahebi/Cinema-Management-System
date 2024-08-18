package org.example.moviereservationsystem.movie;

import jakarta.persistence.EntityNotFoundException;
import org.example.moviereservationsystem.base.BaseDao;
import org.example.moviereservationsystem.actor.ActorEntity;
import org.example.moviereservationsystem.cinema.CinemaEntity;
import org.example.moviereservationsystem.director.DirectorEntity;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieDao extends BaseDao  {
    public MovieEntity getBId(int id) throws EntityNotFoundException{
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        MovieEntity movie = null;
        try {
            transaction = session.beginTransaction();

//            String hql = "select M.name, M.genre, M.rating, M.description, M.director from MovieEntity M where id = :id";
//            Query query = session.createQuery(hql);
//            query.setParameter("id", id);
//            List results = query.getResultList();
//            if (results.size() > 0) {
//                movie = (MovieEntity) results.get(0);
//            }


//                 for when we have cache
            movie = (MovieEntity) session.get(MovieEntity.class, id);
            List<ActorEntity> actors = movie.getActors();
            Hibernate.initialize(actors);
            List<CinemaEntity> cinemas = movie.getCinemas();
            Hibernate.initialize(cinemas);
            DirectorEntity director = (DirectorEntity) session.get(DirectorEntity.class, movie.getDirector().getDirectorId());
            Hibernate.initialize(director);


//            String hql = "SELECT M.actors FROM  MovieEntity M WHERE M.id = :id";
//            Query query = session.createQuery(hql);
//            query.setParameter("id", id);
//            List results = query.list();
//            movie.setActors(results);
//            String hql2 = "SELECT M.cinemas FROM  MovieEntity M WHERE M.id = :id";
//            Query query2 = session.createQuery(hql2);
//            query2.setParameter("id", id);
//            List results2 = query2.list();
//            movie.setCinemas(results2);

            transaction.commit();
        } catch (HibernateException e){
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }catch (NullPointerException | EntityNotFoundException e){
            throw new EntityNotFoundException();
        }
        finally {
            session.close();
        }
        return movie;
    }
}
