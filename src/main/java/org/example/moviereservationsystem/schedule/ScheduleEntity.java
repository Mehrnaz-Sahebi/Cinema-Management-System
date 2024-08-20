package org.example.moviereservationsystem.schedule;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.moviereservationsystem.TableNames;
import org.example.moviereservationsystem.auditorium.AuditoriumColumnNames;
import org.example.moviereservationsystem.auditorium.AuditoriumEntity;
import org.example.moviereservationsystem.base.BaseEntity;
import org.example.moviereservationsystem.cinema.CinemaEntity;
import org.example.moviereservationsystem.movie.MovieColumnNames;
import org.example.moviereservationsystem.movie.MovieEntity;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = TableNames.SCHEDULE)
public class ScheduleEntity implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ScheduleColumnNames.SCHEDULE_ID)
    private int id;
    @ManyToOne
    @JoinColumn(name = AuditoriumColumnNames.AUDITORIUM_ID)
    private AuditoriumEntity auditorium;
    @ManyToOne
    @JoinColumn(name = MovieColumnNames.MOVIE_ID)
    private MovieEntity movie;
    @Column(name = ScheduleColumnNames.TIME)
    private Timestamp time;

}
