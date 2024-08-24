package org.example.moviereservationsystem.address;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.moviereservationsystem.TableNames;
import org.example.moviereservationsystem.base.BaseEntity;
import org.example.moviereservationsystem.cinema.CinemaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Embeddable
public class AddressEntity implements BaseEntity {

    @Column(name = AddressColumnNames.ADDRESS_ID, nullable = false)
    private int id;

    @Column(name = AddressColumnNames.NUMBER)
    private String number;

    @Column(name = AddressColumnNames.ALLEY)
    private String alley;

    @Column(name = AddressColumnNames.STREET)
    private String street;

    @Column(name = AddressColumnNames.CITY)
    private String city;
}
