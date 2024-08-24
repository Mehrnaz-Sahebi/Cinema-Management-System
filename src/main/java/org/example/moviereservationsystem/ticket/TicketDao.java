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

@Repository
public class TicketDao extends BaseDao {
    public static final Logger LOGGER = LoggerFactory.getLogger(TicketDao.class);
    public TicketEntity reserveTicket(int scheduleId,int phoneNumber) throws EntityNotFoundException, TicketException {
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
            if (schedule.getRemainingTicketCount()<=0){
                throw new TicketException("ticket remaining count is zero");
            }
            if (schedule.getPrice()>user.getWallet()){
                throw new TicketException("not enough money for buying the ticket");
            }
            schedule.setRemainingTicketCount(schedule.getRemainingTicketCount()-1);
            user.setWallet(user.getWallet()-schedule.getPrice());
            Query query1 = session.createQuery("update ScheduleEntity S set S.remainingTicketCount =: remaining where S.id =: id");
            query1.setParameter("id", scheduleId);
            query1.setParameter("remaining", schedule.getRemainingTicketCount());
            query1.executeUpdate();
            Query query2 = session.createQuery("update UserEntity U set U.wallet =: wallet where U.id =: id");
            query2.setParameter("id",phoneNumber);
            query2.setParameter("wallet",user.getWallet());
            query2.executeUpdate();
            ticket = new TicketEntity(user,schedule);
            session.persist(ticket);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(LoggerMessageCreator.errorCreating("TicketEntity",ticket.toString()));
        } finally {
            session.close();
        }
        return ticket;
    }
}
