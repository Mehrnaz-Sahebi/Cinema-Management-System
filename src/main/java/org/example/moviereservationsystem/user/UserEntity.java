package org.example.moviereservationsystem.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.moviereservationsystem.TableNames;
import org.example.moviereservationsystem.base.BaseEntity;
import org.example.moviereservationsystem.ticket.TicketEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(name = TableNames.USER)
@Entity
public class UserEntity implements BaseEntity {
    @Id
    //phoneNumber
    @Column(name = UserColumnNames.USER_ID, nullable = false)
    private int id;

    @Column(name = UserColumnNames.FIRST_NAME)
    private String firstName;

    @Column(name = UserColumnNames.LAST_NAME)
    private String lastName;

    @Column(name = UserColumnNames.EMAIL)
    private String email;

    @Column(name = UserColumnNames.PASSWORD)
    private String password;

    @Column(name = UserColumnNames.ROLE)
    private String role;

    @OneToMany(mappedBy = "owner")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TicketEntity> tickets;

    @Column(name = UserColumnNames.WALLET)
    private long wallet;

    @Override
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
    @JsonIgnore
    public Set<TicketEntity> getTickets() {
        return tickets;
    }

    public long getWallet() {
        return wallet;
    }

    public UserEntity(int id, String firstName, String lastName, String email, String password, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}


