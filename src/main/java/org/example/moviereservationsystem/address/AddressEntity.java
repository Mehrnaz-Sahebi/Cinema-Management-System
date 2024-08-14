package org.example.moviereservationsystem.address;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.moviereservationsystem.TableNames;
import org.example.moviereservationsystem.cinema.CinemaEntity;

@NoArgsConstructor
@Setter
@Getter
@Entity
//@Embeddable
@Table(name =TableNames.ADDRESS)
public class AddressEntity {
    @Id
    @Column(name = "address-id")
    private int addressId;
    @Column(name = "number")
    private String number;
    @Column(name = "alley")
    private String alley;
    @Column(name = "street")
    private String street;
    @Column(name = "city")
    private String city;
    @OneToOne(mappedBy = "addressId")
    private CinemaEntity cinema;

}
