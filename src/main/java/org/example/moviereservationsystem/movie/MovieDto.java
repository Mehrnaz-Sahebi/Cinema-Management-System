package org.example.moviereservationsystem.movie;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private int id;
    private String title;
    private String description;
    private int year;
    private String directorFirstName;
    private String directorLastName;
    private String genre;
    private int rating;
    private int lengthInMinutes;
}
