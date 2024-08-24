package org.example.moviereservationsystem.ticket;

import jakarta.persistence.EntityNotFoundException;
import org.example.moviereservationsystem.base.BaseService;
import org.example.moviereservationsystem.schedule.ScheduleDao;
import org.example.moviereservationsystem.schedule.ScheduleEntity;
import org.example.moviereservationsystem.user.UserDao;
import org.example.moviereservationsystem.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService extends BaseService {
    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private ScheduleDao scheduleDao;
    @Autowired
    private UserDao userDao;
    public TicketEntity reserveTicket(int scheduleId,int phoneNumber) throws EntityNotFoundException, TicketException {
        return ticketDao.reserveTicket(scheduleId,phoneNumber);
    }
    public List<TicketEntity> getMyTickets(int phoneNumber){
        return ticketDao.getMyTickets(phoneNumber);
    }
    public void cancelTicket(int phoneNumber, int ticketId) throws EntityNotFoundException, TicketException{
        ticketDao.cancelTicket(phoneNumber,ticketId);
    }
}
