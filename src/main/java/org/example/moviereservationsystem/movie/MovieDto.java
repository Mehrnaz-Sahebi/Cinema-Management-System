package org.example.moviereservationsystem.movie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.moviereservationsystem.actor.ActorColumnNames;
import org.example.moviereservationsystem.actor.ActorEntity;
import org.example.moviereservationsystem.cinema.CinemaColumnNames;
import org.example.moviereservationsystem.cinema.CinemaEntity;
import org.example.moviereservationsystem.director.DirectorEntity;
import org.example.moviereservationsystem.schedule.ScheduleEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private int id;
    private String title;
    private String description;
    private int year;
    private String directorFirstName;
    private String directorLastName;
    private String genre;
    private int rating;
    private int lengthInMinutes;
}
