package org.example.moviereservationsystem.cinema;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.moviereservationsystem.TableNames;
import org.example.moviereservationsystem.address.AddressEntity;
import org.example.moviereservationsystem.movie.MovieEntity;

import java.util.ArrayList;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = TableNames.CINEMA)
public class CinemaEntity {
    @Id
    @Column(name = "cinema-id")
    private int cinemaId;
    @Column(name = "name")
    private String name;
    @Column(name = "address-id")
    @Embedded
    private AddressEntity address;

    @OneToMany(mappedBy = "cinema")
    private ArrayList<AuditoriumEntity> auditoria;

    @ManyToMany (mappedBy = "cinemas")
    private ArrayList<MovieEntity> movies;

}
