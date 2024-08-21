package org.example.moviereservationsystem.schedule;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService  {
    @Autowired
    private ScheduleDao scheduleDao;
    public ScheduleEntity addSchedule(ScheduleEntity schedule) throws ScheduleException, EntityNotFoundException{
        return scheduleDao.addSchedule(schedule);
    }
    public ScheduleEntity getSchedule(int id) throws EntityNotFoundException{
        return scheduleDao.getById(id, ScheduleEntity.class);
    }
}
