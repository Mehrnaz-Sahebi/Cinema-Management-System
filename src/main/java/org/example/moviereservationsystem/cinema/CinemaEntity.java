package org.example.moviereservationsystem.cinema;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.moviereservationsystem.TableNames;
import org.example.moviereservationsystem.address.AddressEntity;
import org.example.moviereservationsystem.auditorium.AuditoriumEntity;
import org.example.moviereservationsystem.base.BaseEntity;
import org.example.moviereservationsystem.movie.MovieEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = TableNames.CINEMA)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CinemaEntity implements BaseEntity {
    @Id
    @Column(name = "cinema-id", nullable = false)
    private int id;
    @Column(name = "name")
    private String name;
    @Embedded
    private AddressEntity addressId;
    @Transient
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "cinema")
    private List<AuditoriumEntity> auditoria;
    @Transient
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToMany (mappedBy = "cinemas")
    private List<MovieEntity> movies;

    @Override
    public String toString() {
        return "CinemaEntity{" +
                "cinemaId=" + id +
                ", name='" + name + '\'' +
                ", addressId=" + addressId +
                ", auditoria=" + auditoria +
                ", movies=" + movies +
                '}';
    }
}
