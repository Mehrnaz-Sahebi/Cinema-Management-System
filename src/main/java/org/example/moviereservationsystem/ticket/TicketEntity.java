package org.example.moviereservationsystem.ticket;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.moviereservationsystem.TableNames;
import org.example.moviereservationsystem.base.BaseEntity;

import org.example.moviereservationsystem.schedule.ScheduleColumnNames;
import org.example.moviereservationsystem.schedule.ScheduleEntity;
import org.example.moviereservationsystem.user.UserColumnNames;
import org.example.moviereservationsystem.user.UserEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TableNames.TICKET)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TicketEntity implements BaseEntity {
    @Id
    @Column(name = TicketColumnNames.TICKET_ID , nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = ScheduleColumnNames.SCHEDULE_ID)
    private ScheduleEntity schedule;
    @ManyToOne
    @JoinColumn(name = UserColumnNames.USER_ID)
    private UserEntity owner;
}
