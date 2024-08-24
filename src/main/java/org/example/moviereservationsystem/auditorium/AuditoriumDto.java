package org.example.moviereservationsystem.auditorium;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuditoriumDto {

    private String name;
    private int capacity;
    private int rowCount;
}
