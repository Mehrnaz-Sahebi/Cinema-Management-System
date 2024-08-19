package org.example.moviereservationsystem.user;

import jakarta.persistence.EntityNotFoundException;
import org.example.moviereservationsystem.base.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


@Repository
public class UserDao extends BaseDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);
    public UserEntity getById(int id) throws EntityNotFoundException {
        return super.getById(id, UserEntity.class);
    }
}
