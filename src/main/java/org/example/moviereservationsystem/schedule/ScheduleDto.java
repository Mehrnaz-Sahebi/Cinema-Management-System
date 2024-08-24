package org.example.moviereservationsystem.schedule;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.moviereservationsystem.auditorium.AuditoriumColumnNames;
import org.example.moviereservationsystem.auditorium.AuditoriumEntity;
import org.example.moviereservationsystem.movie.MovieColumnNames;
import org.example.moviereservationsystem.movie.MovieEntity;

import java.sql.Time;
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
