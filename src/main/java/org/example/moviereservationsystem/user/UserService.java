package org.example.moviereservationsystem.user;

import jakarta.persistence.EntityNotFoundException;
import org.example.moviereservationsystem.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService {
    @Autowired
    private UserDao userDao;
    public UserEntity getById(int id) throws EntityNotFoundException {
        return userDao.getById(id);
    }
}
