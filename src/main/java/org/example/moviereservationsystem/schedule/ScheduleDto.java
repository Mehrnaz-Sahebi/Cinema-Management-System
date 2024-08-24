package org.example.moviereservationsystem.schedule;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.moviereservationsystem.auditorium.AuditoriumEntity;
import org.example.moviereservationsystem.movie.MovieEntity;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class ScheduleDto {
    private AuditoriumEntity auditorium;
    private MovieEntity movie;
    private Timestamp startingTime;
    private Timestamp endingTime;
    private long price;
}
