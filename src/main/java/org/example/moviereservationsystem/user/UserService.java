package org.example.moviereservationsystem.user;

import jakarta.persistence.EntityNotFoundException;
import org.example.moviereservationsystem.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    public UserEntity getById(int id) throws EntityNotFoundException {
        return userDao.getById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        UserEntity userEntity = null;
        try {
            userEntity = userDao.getById(Integer.parseInt(phoneNumber));
        } catch (EntityNotFoundException e){
            throw new UsernameNotFoundException("User not found");
        }
        return new UserEntityPrincipal(userEntity);
    }
}
