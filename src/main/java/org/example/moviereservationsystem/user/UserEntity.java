package org.example.moviereservationsystem.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.moviereservationsystem.TableNames;
import org.example.moviereservationsystem.base.BaseEntity;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = TableNames.USER)
@Entity
public class UserEntity implements BaseEntity {
    @Id
    //phoneNumber
    @Column (name = UserColumnNames.USER_ID, nullable = false)
    private int id;
    @Column (name = UserColumnNames.FIRST_NAME)
    private String firstName;
    @Column (name = UserColumnNames.LAST_NAME)
    private String lastName;
    @Column (name = UserColumnNames.EMAIL)
    private String email;
    @Column (name = UserColumnNames.PASSWORD)
    private String password;
    @Column (name = UserColumnNames.ROLE)
    private String role;

    public UserEntity(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UserEntity(String firstName, String lastName, String email, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}


