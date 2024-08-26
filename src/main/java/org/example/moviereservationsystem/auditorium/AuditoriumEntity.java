package org.example.moviereservationsystem.auditorium;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.moviereservationsystem.TableNames;
import org.example.moviereservationsystem.base.BaseEntity;
import org.example.moviereservationsystem.cinema.CinemaColumnNames;
import org.example.moviereservationsystem.cinema.CinemaEntity;
import org.example.moviereservationsystem.schedule.ScheduleEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;
import java.util.Set;


@NoArgsConstructor
@Setter
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = TableNames.AUDITORIUM)
public class AuditoriumEntity implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "auditorium")
    private Set<ScheduleEntity> schedules;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getRowCount() {
        return rowCount;
    }

    public CinemaEntity getCinema() {
        return cinema;
    }
    @JsonIgnore
    public Set<ScheduleEntity> getSchedules() {
        return schedules;
    }

    public AuditoriumEntity(String name, int capacity, int rowCount) {
        this.name = name;
        this.capacity = capacity;
        this.rowCount = rowCount;
    }
}
