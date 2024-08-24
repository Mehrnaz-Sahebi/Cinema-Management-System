package org.example.moviereservationsystem.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
