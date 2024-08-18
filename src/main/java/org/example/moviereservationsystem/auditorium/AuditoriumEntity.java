package org.example.moviereservationsystem.auditorium;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.moviereservationsystem.TableNames;
import org.example.moviereservationsystem.base.BaseEntity;
import org.example.moviereservationsystem.cinema.CinemaEntity;


@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = TableNames.AUDITORIUM)
public class AuditoriumEntity implements BaseEntity {
    @Id
    @Column(name = "auditorium-id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "capacity")
    private int capacity;
    @Column(name = "row-count")
    private int rowCount;

    @ManyToOne
    @JoinColumn(name = "cinema-id")
    private CinemaEntity cinema;

}
