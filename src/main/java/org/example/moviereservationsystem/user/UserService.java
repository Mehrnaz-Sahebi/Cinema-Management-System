package org.example.moviereservationsystem.user;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.example.moviereservationsystem.base.BaseService;
import org.example.moviereservationsystem.authentication.MyPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;


@Service
public class UserService extends BaseService implements UserDetailsManager {
    @Autowired
    private UserDao userDao;

    @Autowired
    private MyPasswordEncoder passwordEncoder;

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
        return new UserEntityDetails(userEntity);
    }
    public UserEntity addUser(UserEntity userEntity) throws EntityExistsException {
        String password = userEntity.getPassword();
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setRole(UserRoles.CUSTOMER);
        UserEntity userToReturn = null;
        userToReturn = userDao.addEntity(userEntity);
        return userToReturn;
    }
    public UserEntity changeRole(int phoneNumber, String newRole) throws EntityNotFoundException{
        return userDao.changeRole(phoneNumber, newRole);
    }
    public void deleteUser(int phoneNumber) throws EntityNotFoundException{
        userDao.deleteEntity("id",Integer.toString(phoneNumber),true,UserEntity.class);
    }

    @Override
    public void createUser(UserDetails user) throws EntityExistsException {
        UserEntity userToCreate = ((UserEntityDetails) user).getUserEntity();
        addUser(userToCreate);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {
        deleteUser(Integer.parseInt(username));
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }
}
