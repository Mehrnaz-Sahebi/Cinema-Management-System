package org.example.moviereservationsystem.schedule;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.moviereservationsystem.LoggerMessageCreator;
import org.example.moviereservationsystem.auditorium.AuditoriumEntity;
import org.example.moviereservationsystem.base.BaseDao;
import org.example.moviereservationsystem.cinema.CinemaEntity;
import org.example.moviereservationsystem.movie.MovieEntity;
import org.hibernate.HibernateException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScheduleDao extends BaseDao {
    public static final Logger LOGGER = LoggerFactory.getLogger(ScheduleDao.class);
    public ScheduleEntity addSchedule(ScheduleEntity schedule) throws ScheduleException, EntityNotFoundException {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        ScheduleEntity scheduleToReturn = null;
        try {
            transaction = session.beginTransaction();
            String hql1 = "FROM AuditoriumEntity A WHERE A.name =: name";
            Query query1 = session.createQuery(hql1);
            query1.setParameter("name", schedule.getAuditorium().getName());
            List results1 = query1.list();
            if (results1.isEmpty()) {
                throw new EntityNotFoundException("auditorium");
            }
            CinemaEntity cinema = ((AuditoriumEntity) results1.get(0)).getCinema();
            String hql2 = "FROM MovieEntity M WHERE M.title =: title";
            Query query2 = session.createQuery(hql2);
            query2.setParameter("title", schedule.getMovie().getTitle());
            List results2 = query2.list();
            if (results2.isEmpty()) {
                throw new EntityNotFoundException("movie");
            }
            MovieEntity movie = (MovieEntity) results2.get(0);
            if (!movie.getCinemas().contains(cinema)){
                throw new ScheduleException("The cinema "+schedule.getAuditorium().getCinema().getName()+ " doesn't have movie "+movie.getTitle()+".");
            }
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ScheduleEntity> cq = cb.createQuery(ScheduleEntity.class);
            Root<ScheduleEntity> root = cq.from(ScheduleEntity.class);
            Predicate startingTimeAfter = cb.greaterThanOrEqualTo(root.get("startingTime"), schedule.getStartingTime());
            Predicate startingTimeBefore = cb.lessThanOrEqualTo(root.get("startingTime"), schedule.getEndingTime());
            Predicate endTimeAfter = cb.lessThanOrEqualTo(root.get("endingTime"), schedule.getEndingTime());
            Predicate endTimeBefore = cb.greaterThanOrEqualTo(root.get("endingTime"), schedule.getStartingTime());
            Predicate auditoriumEquals = cb.equal(root.get("auditorium"), schedule.getAuditorium());
            cq.select(root).where(cb.and(auditoriumEquals,cb.or(cb.and(startingTimeAfter,startingTimeBefore), cb.and(endTimeBefore, endTimeAfter))));
            List<ScheduleEntity> results3 = session.createQuery(cq).getResultList();
            if (!results3.isEmpty()) {
                throw new ScheduleException("There's another schedule in that time in that auditorium.");
            }
            session.persist(schedule);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(LoggerMessageCreator.errorCreating("Auditorium", schedule.toString()), e);
            return null;
        } finally {
            session.close();
        }
        return schedule;
    }

}
