package org.example.moviereservationsystem.director;

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
@Table (name = TableNames.DIRECTOR)
public class DirectorEntity {
    @Id
    @Column(name = "director-id")
    private int directorId;
    @Column(name = "first-name")
    private String firstName;
    @Column(name = "last-name")
    private String lastName;

    @OneToMany (mappedBy = "director")
    private ArrayList<MovieEntity> movieList;

}
