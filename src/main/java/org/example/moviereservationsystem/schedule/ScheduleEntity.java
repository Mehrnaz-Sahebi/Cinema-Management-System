package org.example.moviereservationsystem.schedule;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.moviereservationsystem.TableNames;
import org.example.moviereservationsystem.auditorium.AuditoriumColumnNames;
import org.example.moviereservationsystem.auditorium.AuditoriumEntity;
import org.example.moviereservationsystem.base.BaseEntity;
import org.example.moviereservationsystem.movie.MovieColumnNames;
import org.example.moviereservationsystem.movie.MovieEntity;
import org.example.moviereservationsystem.ticket.TicketEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.sql.Timestamp;
import java.util.Set;

@Setter
@NoArgsConstructor
@Entity
@Table(name = TableNames.SCHEDULE)
public class ScheduleEntity implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ScheduleColumnNames.SCHEDULE_ID)
    private int id;

    @ManyToOne
    @JoinColumn(name = AuditoriumColumnNames.AUDITORIUM_ID)
    private AuditoriumEntity auditorium;

    @ManyToOne
    @JoinColumn(name = MovieColumnNames.MOVIE_ID)
    private MovieEntity movie;

    @Column(name = ScheduleColumnNames.STARTING_TIME)
    private Timestamp startingTime;

    @Column(name = ScheduleColumnNames.ENDING_TIME)
    private Timestamp endingTime;

    @Column(name = ScheduleColumnNames.REMAINING_TICKET_COUNT)
    private int remainingTicketCount;

    @OneToMany(mappedBy = "schedule")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TicketEntity> tickets;

    @Column(name = ScheduleColumnNames.PRICE)
    private long price;

    @Override
    public int getId() {
        return id;
    }

    public AuditoriumEntity getAuditorium() {
        return auditorium;
    }

    public MovieEntity getMovie() {
        return movie;
    }

    public Timestamp getStartingTime() {
        return startingTime;
    }

    public Timestamp getEndingTime() {
        return endingTime;
    }

    public int getRemainingTicketCount() {
        return remainingTicketCount;
    }
    @JsonIgnore
    public Set<TicketEntity> getTickets() {
        return tickets;
    }

    public long getPrice() {
        return price;
    }

    public ScheduleEntity(Timestamp endingTime, Timestamp startingTime, MovieEntity movie, AuditoriumEntity auditorium, long price) {
        this.endingTime = endingTime;
        this.startingTime = startingTime;
        this.movie = movie;
        this.auditorium = auditorium;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ScheduleEntity{" +
                "auditorium=" + auditorium +
                ", movie=" + movie +
                ", startingTime=" + startingTime +
                ", endingTime=" + endingTime +
                ", remainingTicketCount=" + remainingTicketCount +
                '}';
    }
}
