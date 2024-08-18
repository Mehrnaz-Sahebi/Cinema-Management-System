package org.example.moviereservationsystem.auditorium;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.moviereservationsystem.TableNames;
import org.example.moviereservationsystem.base.BaseEntity;
import org.example.moviereservationsystem.cinema.CinemaColumnNames;
import org.example.moviereservationsystem.cinema.CinemaEntity;


@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = TableNames.AUDITORIUM)
public class AuditoriumEntity implements BaseEntity {
    @Id
    @Column(name = AuditoriumColumnNames.AUDITORIUM_ID, nullable = false)
    private int id;
    @Column(name = AuditoriumColumnNames.NAME)
    private String name;
    @Column(name = AuditoriumColumnNames.CAPACITY)
    private int capacity;
    @Column(name = AuditoriumColumnNames.ROW_COUNT)
    private int rowCount;

    @ManyToOne
    @JoinColumn(name = CinemaColumnNames.CINEMA_ID)
    private CinemaEntity cinema;

}
