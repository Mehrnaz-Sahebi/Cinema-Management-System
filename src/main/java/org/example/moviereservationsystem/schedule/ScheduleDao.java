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

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public class ScheduleDao extends BaseDao {
    public static final Logger LOGGER = LoggerFactory.getLogger(ScheduleDao.class);

    public ScheduleEntity addSchedule(ScheduleEntity schedule) throws ScheduleException, EntityNotFoundException {
        Session session = getSession();
        ScheduleEntity scheduleToReturn = null;
        try {
            Query query1 = session.createQuery("FROM AuditoriumEntity A WHERE A.name =: name");
            query1.setParameter("name", schedule.getAuditorium().getName());
            List results1 = query1.list();
            if (results1.isEmpty()) {
                throw new EntityNotFoundException("auditorium");
            }
            CinemaEntity cinema = ((AuditoriumEntity) results1.get(0)).getCinema();
            Query query2 = session.createQuery("FROM MovieEntity M WHERE M.title =: title");
            query2.setParameter("title", schedule.getMovie().getTitle());
            List results2 = query2.list();
            if (results2.isEmpty()) {
                throw new EntityNotFoundException("movie");
            }
            MovieEntity movie = (MovieEntity) results2.get(0);
            if (!movie.getCinemas().contains(cinema)) {
                throw new ScheduleException("The cinema " + schedule.getAuditorium().getCinema().getName() + " doesn't have movie " + movie.getTitle() + ".");
            }
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ScheduleEntity> cq = cb.createQuery(ScheduleEntity.class);
            Root<ScheduleEntity> root = cq.from(ScheduleEntity.class);
            Predicate startingTimeAfter = cb.greaterThanOrEqualTo(root.get("startingTime"), schedule.getStartingTime());
            Predicate startingTimeBefore = cb.lessThanOrEqualTo(root.get("startingTime"), schedule.getEndingTime());
            Predicate endTimeAfter = cb.lessThanOrEqualTo(root.get("endingTime"), schedule.getEndingTime());
            Predicate endTimeBefore = cb.greaterThanOrEqualTo(root.get("endingTime"), schedule.getStartingTime());
            Predicate auditoriumEquals = cb.equal(root.get("auditorium"), schedule.getAuditorium());
            cq.select(root).where(cb.and(auditoriumEquals, cb.or(cb.and(startingTimeAfter, startingTimeBefore), cb.and(endTimeBefore, endTimeAfter))));
            List<ScheduleEntity> results3 = session.createQuery(cq).getResultList();
            if (!results3.isEmpty()) {
                throw new ScheduleException("There's another schedule in that time in that auditorium.");
            }
            session.persist(schedule);
        } catch (HibernateException e) {
            LOGGER.error(LoggerMessageCreator.errorCreating("AuditoriumEntity", schedule.toString()), e);
            return null;
        }
        return schedule;
    }

    public List<ScheduleEntity> getAllSchedules() {
        Session session = getSessionFactory().openSession();
        List results1 = null;
        try {
            Query query1 = session.createQuery("FROM ScheduleEntity");
            results1 = query1.list();
        } catch (HibernateException e) {
            LOGGER.error(LoggerMessageCreator.errorGettingAll("ScheduleEntity"), e);
            return null;
        }
        return (List<ScheduleEntity>) results1;
    }

    public List<ScheduleEntity> getSchedulesForMovie(String movieTitle) throws EntityNotFoundException {
        Session session = getSession();
        List results2 = null;
        try {
            Query query1 = session.createQuery("FROM MovieEntity M WHERE M.title =: title");
            query1.setParameter("title", movieTitle);
            List results1 = query1.list();
            if (results1.isEmpty()) {
                throw new EntityNotFoundException();
            }
            MovieEntity movie = (MovieEntity) results1.get(0);
            Query query2 = session.createQuery("FROM ScheduleEntity S WHERE S.movie =: movie");
            query2.setParameter("movie", movie);
            results2 = query2.list();
        } catch (HibernateException e) {
            LOGGER.error(LoggerMessageCreator.errorGettingAllWith("ScheduleEntity", "MovieTitle", movieTitle), e);
            return null;
        }
        return (List<ScheduleEntity>) results2;
    }

    public List<ScheduleEntity> getSchedulesForCinema(String cinemaName) throws EntityNotFoundException {
        Session session = getSession();
        List results2 = null;
        Query query1 = session.createQuery("FROM CinemaEntity C WHERE C.name =: name");
        query1.setParameter("name", cinemaName);
        List results1 = query1.list();
        if (results1.isEmpty()) {
            throw new EntityNotFoundException();
        }
        CinemaEntity cinema = (CinemaEntity) results1.get(0);
        Query query2 = session.createQuery("FROM ScheduleEntity S WHERE S.auditorium.cinema =: cinema");
        query2.setParameter("cinema", cinema);
        results2 = query2.list();
        return (List<ScheduleEntity>) results2;
    }

    public List<ScheduleEntity> getSchedulesForDate(Date date) {
        Session session = getSession();
        List results2 = null;
        Query query2 = session.createQuery("FROM ScheduleEntity S WHERE year (S.startingTime) =: dYear and month (S.startingTime)=: dMonth year and day (S.startingTime)=: dDay");
        query2.setParameter("dYear", date.getYear());
        query2.setParameter("dMonth", date.getMonth());
        query2.setParameter("dDay", date.getDate());
        results2 = query2.list();
        return (List<ScheduleEntity>) results2;
    }

}
