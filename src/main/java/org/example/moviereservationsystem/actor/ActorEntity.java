package org.example.moviereservationsystem.actor;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.moviereservationsystem.base.BaseEntity;
import org.example.moviereservationsystem.TableNames;
import org.example.moviereservationsystem.movie.MovieEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = TableNames.ACTOR)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ActorEntity implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ActorColumnNames.ACTOR_ID, nullable = false)
    private int id;
    @Column(name = ActorColumnNames.FIRST_NAME )
    private String firstName;
    @Column(name = ActorColumnNames.LAST_NAME)
    private String lastName;
    @Transient
    @Column(name = ActorColumnNames.MOVIES)
    @ManyToMany(mappedBy = "actors")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MovieEntity> movies;

    public ActorEntity(String firstName, String lastName, Set<MovieEntity> movies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.movies = movies;
    }
}
