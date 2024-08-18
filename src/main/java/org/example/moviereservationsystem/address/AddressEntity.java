package org.example.moviereservationsystem.address;

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
@Embeddable
public class AddressEntity implements BaseEntity {
    @Column(name = "address-id", nullable = false)
    private int id;
    @Column(name = "number")
    private String number;
    @Column(name = "alley")
    private String alley;
    @Column(name = "street")
    private String street;
    @Column(name = "city")
    private String city;

}
