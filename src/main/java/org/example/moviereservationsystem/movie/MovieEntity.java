package org.example.moviereservationsystem.movie;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.moviereservationsystem.TableNames;
import org.example.moviereservationsystem.actor.ActorEntity;
import org.example.moviereservationsystem.cinema.CinemaEntity;
import org.example.moviereservationsystem.director.DirectorEntity;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = TableNames.MOVIE)

public class MovieEntity {
    @Id
    @Column(name = "movie-id")
    private int movieId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "year")
    private int year;
    @ManyToOne
    @JoinColumn(name = "director-id")
    private DirectorEntity director;
    @Column(name = "genre")
    private String genre;
    @Column(name = "rating")
    private int rating;

    @ManyToMany
    @JoinTable(
            name = "movie-actor",
            joinColumns = { @JoinColumn(name = "movie-id") },
            inverseJoinColumns = { @JoinColumn(name = "actor-id") }
    )
    private List<ActorEntity> actors;
    @ManyToMany
    @JoinTable(
            name = "movie-cinema",
            joinColumns = { @JoinColumn(name = "movie-id") },
            inverseJoinColumns = { @JoinColumn(name = "cinema-id") }
    )
    private List<CinemaEntity> cinemas;

    @Override
    public String toString() {
        return "MovieEntity{" +
                "movieId=" + movieId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", director=" + director +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                ", actors=" + actors +
                ", cinemas=" + cinemas +
                '}';
    }
}

