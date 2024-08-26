package org.example.moviereservationsystem.cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.moviereservationsystem.TableNames;
import org.example.moviereservationsystem.address.AddressEntity;
import org.example.moviereservationsystem.auditorium.AuditoriumEntity;
import org.example.moviereservationsystem.base.BaseEntity;
import org.example.moviereservationsystem.movie.MovieEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter

@Entity
@Table(name = TableNames.CINEMA)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CinemaEntity implements BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = CinemaColumnNames.CINEMA_ID, nullable = false)
    private int id;

    @Column(name = CinemaColumnNames.NAME)
    private String name;

    @Embedded
    private AddressEntity addressId;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "cinema")
    private Set<AuditoriumEntity> auditoria;


    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToMany(mappedBy = "cinemas")
    private Set<MovieEntity> movies;

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AddressEntity getAddressId() {
        return addressId;
    }
    @JsonIgnore
    public Set<AuditoriumEntity> getAuditoria() {
        return auditoria;
    }
    @JsonIgnore
    public Set<MovieEntity> getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "CinemaEntity{" +
                "cinemaId=" + id +
                ", title='" + name + '\'' +
                ", addressId=" + addressId +
                ", auditoria=" + auditoria +
                ", movies=" + movies +
                '}';
    }

    public CinemaEntity(String name, AddressEntity addressId, Set<AuditoriumEntity> auditoria, Set<MovieEntity> movies) {
        this.name = name;
        this.addressId = addressId;
        this.auditoria = auditoria;
        this.movies = movies;
    }
}
