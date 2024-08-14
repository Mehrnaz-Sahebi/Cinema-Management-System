package org.example.moviereservationsystem.actor;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.moviereservationsystem.TableNames;
import org.example.moviereservationsystem.movie.MovieEntity;
import java.util.ArrayList;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = TableNames.ACTOR)
public class ActorEntity {

    @Id
    @Column(name = "actor-id")
    private int actorId;
    @Column(name = "first-name")
    private String firstName;
    @Column(name = "last-name")
    private String lastName;
    @Column(name = "movies")
    @ManyToMany(mappedBy = "actors")
    private ArrayList<MovieEntity> movies;

}
