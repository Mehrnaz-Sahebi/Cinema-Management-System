package org.example.moviereservationsystem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Table(name = TableNames.USER)
@Entity
public class UserEntity {
    @Id
    @Column (name = "user-id")
    private String phoneNumber;
    @Column (name = "first-name")
    private String firstName;
    @Column (name = "last-name")
    private String lastName;
    @Column (name = "email")
    private String email;
    @Column (name = "password")
    private String password;
}


