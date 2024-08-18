package org.example.moviereservationsystem.user;

import jakarta.persistence.EntityNotFoundException;
import org.example.moviereservationsystem.base.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao {
    public UserEntity getById(int id) throws EntityNotFoundException {
        return super.getById(id, UserEntity.class);
    }
}
