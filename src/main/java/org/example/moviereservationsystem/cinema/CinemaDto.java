package org.example.moviereservationsystem.cinema;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CinemaDto {

    private String name;
    private int addressId;
    private String number;
    private String alley;
    private String street;
    private String city;
}
