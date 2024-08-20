package org.example.moviereservationsystem.director;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.moviereservationsystem.TableNames;
import org.example.moviereservationsystem.movie.MovieEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Embeddable
public class DirectorEntity {
    @Column(name = DirectorColumnNames.DIRECTOR_FIRST_NAME)
    private String firstName;
    @Column(name = DirectorColumnNames.DIRECTOR_LAST_NAME)
    private String lastName;


    @Override
    public String toString() {
        return "DirectorEntity{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}
