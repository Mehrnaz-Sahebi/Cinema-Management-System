package org.example.moviereservationsystem.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.moviereservationsystem.TableNames;
import org.example.moviereservationsystem.base.BaseEntity;

@NoArgsConstructor
@Setter
@Getter
@Table(name = TableNames.USER)
@Entity
public class UserEntity extends BaseEntity {
    @Id
    //phoneNumber
    @Column (name = "user-id")
    private int id;
    @Column (name = "first-name")
    private String firstName;
    @Column (name = "last-name")
    private String lastName;
    @Column (name = "email")
    private String email;
    @Column (name = "password")
    private String password;
}


