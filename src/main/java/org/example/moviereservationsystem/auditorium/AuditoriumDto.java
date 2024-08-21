package org.example.moviereservationsystem.auditorium;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.moviereservationsystem.cinema.CinemaColumnNames;
import org.example.moviereservationsystem.cinema.CinemaEntity;
import org.example.moviereservationsystem.schedule.ScheduleEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class AuditoriumDto {

    private String name;
    private int capacity;
    private int rowCount;


}
