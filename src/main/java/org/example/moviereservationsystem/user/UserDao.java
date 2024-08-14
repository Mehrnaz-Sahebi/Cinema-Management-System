package org.example.moviereservationsystem.user;

import org.example.moviereservationsystem.Dao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends Dao {
    public UserEntity getUSerById(int id) {
        UserEntity user = super.getById(id,UserEntity.class);
        if (user == null) return new UserEntity();
        return user;
    }

}
