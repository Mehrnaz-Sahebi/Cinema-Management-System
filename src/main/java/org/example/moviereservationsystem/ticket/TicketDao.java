package org.example.moviereservationsystem.ticket;

import jakarta.persistence.EntityNotFoundException;
import org.example.moviereservationsystem.LoggerMessageCreator;
import org.example.moviereservationsystem.base.BaseDao;
import org.example.moviereservationsystem.schedule.ScheduleEntity;
import org.example.moviereservationsystem.user.UserEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketDao extends BaseDao {
    public static final Logger LOGGER = LoggerFactory.getLogger(TicketDao.class);

    public TicketEntity reserveTicket(int scheduleId, int phoneNumber) throws EntityNotFoundException, TicketException {
        TicketEntity ticket = null;
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            UserEntity user = session.get(UserEntity.class, phoneNumber);
            if (user == null) {
                throw new EntityNotFoundException("user");
            }
            ScheduleEntity schedule = session.get(ScheduleEntity.class, scheduleId);
            if (schedule == null) {
                throw new EntityNotFoundException("schedule");
            }
            if (schedule.getRemainingTicketCount() <= 0) {
                throw new TicketException("ticket remaining count is zero");
            }
            if (schedule.getPrice() > user.getWallet()) {
                throw new TicketException("not enough money for buying the ticket");
            }
            schedule.setRemainingTicketCount(schedule.getRemainingTicketCount() - 1);
            user.setWallet(user.getWallet() - schedule.getPrice());
            ticket = new TicketEntity(user, schedule);
            session.persist(ticket);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(LoggerMessageCreator.errorCreating("TicketEntity", ticket.toString()));
        } finally {
            session.close();
        }
        return ticket;
    }

    public List<TicketEntity> getMyTickets(int phoneNumber) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        List<TicketEntity> tickets = null;
        try {
            transaction = session.beginTransaction();
            UserEntity user = session.get(UserEntity.class, phoneNumber);
            Query query = session.createQuery("from TicketEntity T where T.owner =: user");
            query.setParameter("user", user);
            tickets = (List<TicketEntity>) query.list();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(LoggerMessageCreator.errorGettingAllWith("TicketEntity", "user-id", Integer.toString(phoneNumber)));
        } finally {
            session.close();
        }
        return tickets;
    }

    public void cancelTicket(int phoneNumber, int ticketId) throws EntityNotFoundException, TicketException {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        TicketEntity ticket = null;
        try {
            transaction = session.beginTransaction();
            ticket = session.get(TicketEntity.class, ticketId);
            if (ticket == null) {
                throw new EntityNotFoundException();
            }
            UserEntity user = ticket.getOwner();
            if (user.getId() != phoneNumber) {
                throw new TicketException("Only the owner can delete the ticket");
            }
            ScheduleEntity schedule = ticket.getSchedule();
            user.setWallet(user.getWallet() + schedule.getPrice());
            schedule.setRemainingTicketCount(schedule.getRemainingTicketCount() + 1);
            session.delete(ticket);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(LoggerMessageCreator.errorDeleting("TicketEntity", ticket.toString()));
        } finally {
            session.close();
        }
    }
}
