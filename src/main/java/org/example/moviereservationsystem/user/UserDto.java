package org.example.moviereservationsystem.user;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {

    //phoneNumber
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
