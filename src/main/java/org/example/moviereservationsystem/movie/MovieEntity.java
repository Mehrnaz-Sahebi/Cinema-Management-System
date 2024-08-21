package org.example.moviereservationsystem.movie;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.moviereservationsystem.TableNames;
import org.example.moviereservationsystem.actor.ActorColumnNames;
import org.example.moviereservationsystem.actor.ActorEntity;
import org.example.moviereservationsystem.base.BaseEntity;
import org.example.moviereservationsystem.cinema.CinemaColumnNames;
import org.example.moviereservationsystem.cinema.CinemaEntity;
import org.example.moviereservationsystem.director.DirectorEntity;
import org.example.moviereservationsystem.schedule.ScheduleEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = TableNames.MOVIE)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MovieEntity implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = MovieColumnNames.MOVIE_ID, nullable = false)
    private int id;
    @Column(name = MovieColumnNames.TITLE)
    private String title;
    @Column(name = MovieColumnNames.DESCRIPTION)
    private String description;
    @Column(name = MovieColumnNames.YEAR)
    private int year;
    @Embedded
    private DirectorEntity directorId;
    @Column(name = MovieColumnNames.GENRE)
    private String genre;
    @Column(name = MovieColumnNames.RATING)
    private int rating;
    @Column(name = MovieColumnNames.LENGTH_IN_MINUTES)
    private int lengthInMinutes;
    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "movie-actor",
            joinColumns = { @JoinColumn(name = MovieColumnNames.MOVIE_ID) },
            inverseJoinColumns = { @JoinColumn(name = ActorColumnNames.ACTOR_ID) }
    )
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<ActorEntity> actors;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "movie-cinema",
            joinColumns = { @JoinColumn(name = MovieColumnNames.MOVIE_ID) },
            inverseJoinColumns = { @JoinColumn(name = CinemaColumnNames.CINEMA_ID) }
    )
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CinemaEntity> cinemas;

    @OneToMany(mappedBy = "movie")
    @Transient
    private List<ScheduleEntity> schedules;


    @Override
    public String toString() {
        return "MovieEntity{" +
                "movieId=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", directorId=" + directorId +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                ", actors=" + actors +
                ", cinemas=" + cinemas +
                '}';
    }

    public MovieEntity(String title, String description, int year, DirectorEntity directorId, String genre, int rating, int lengthInMinutes) {
        this.title = title;
        this.description = description;
        this.year = year;
        this.directorId = directorId;
        this.genre = genre;
        this.rating = rating;
        this.lengthInMinutes = lengthInMinutes;
    }
}

