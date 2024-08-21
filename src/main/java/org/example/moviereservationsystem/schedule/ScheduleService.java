package org.example.moviereservationsystem.schedule;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    public List<ScheduleEntity> getAllSchedules(){
        return scheduleDao.getAllSchedules();
    }
    public List<ScheduleEntity> getSchedulesForMovie(String movieTitle) throws EntityNotFoundException{
        return scheduleDao.getSchedulesForMovie(movieTitle);
    }
    public List<ScheduleEntity> getSchedulesForCinema(String cinemaName) throws EntityNotFoundException{
        return scheduleDao.getSchedulesForCinema(cinemaName);
    }
    public List<ScheduleEntity> getSchedulesForDate(Date date){
        return scheduleDao.getSchedulesForDate(date);
    }
}
