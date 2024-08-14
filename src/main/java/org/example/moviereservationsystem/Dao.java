package org.example.moviereservationsystem;

import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;

@Repository
public class Dao {
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Getter
    private SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);

}
