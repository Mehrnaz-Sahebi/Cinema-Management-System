package org.example.moviereservationsystem.movie;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.moviereservationsystem.TableNames;
import org.example.moviereservationsystem.actor.ActorEntity;
import org.example.moviereservationsystem.base.BaseEntity;
import org.example.moviereservationsystem.cinema.CinemaEntity;
import org.example.moviereservationsystem.director.DirectorEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = TableNames.MOVIE)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MovieEntity implements BaseEntity {
    @Id
    @Column(name = "movie-id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "year")
    private int year;
//    @ManyToOne
//    @JoinColumn(name = "directorId-id")
    @Embedded
    private DirectorEntity directorId;
    @Column(name = "genre")
    private String genre;
    @Column(name = "rating")
    private int rating;
    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "movie-actor",
            joinColumns = { @JoinColumn(name = "movie-id") },
            inverseJoinColumns = { @JoinColumn(name = "actor-id") }
    )
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<ActorEntity> actors;
    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "movie-cinema",
            joinColumns = { @JoinColumn(name = "movie-id") },
            inverseJoinColumns = { @JoinColumn(name = "cinema-id") }
    )
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<CinemaEntity> cinemas;

    @Override
    public String toString() {
        return "MovieEntity{" +
                "movieId=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", directorId=" + directorId +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                ", actors=" + actors +
                ", cinemas=" + cinemas +
                '}';
    }
}

