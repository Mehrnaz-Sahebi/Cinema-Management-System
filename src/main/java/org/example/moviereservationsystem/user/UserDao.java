package org.example.moviereservationsystem.user;

import jakarta.persistence.EntityNotFoundException;
import org.example.moviereservationsystem.LoggerMessageCreator;
import org.example.moviereservationsystem.base.BaseDao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


@Repository
public class UserDao extends BaseDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

    public UserEntity getById(int id) throws EntityNotFoundException {
        return super.getById(id, UserEntity.class);
    }

    public UserEntity changeRole(int phoneNumber, String role) throws EntityNotFoundException {
        if (!role.equals(UserRoles.ADMIN) && !role.equals(UserRoles.CUSTOMER) && !role.equals(UserRoles.MANAGER)) {
            throw new EntityNotFoundException("role");
        }
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        UserEntity userToReturn = null;
        try {
            transaction = session.beginTransaction();
            userToReturn = session.get(UserEntity.class, phoneNumber);
            if (userToReturn == null) {
                throw new EntityNotFoundException("user");
            }
            userToReturn.setRole(role);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(LoggerMessageCreator.errorUpdating("UserEntity", Integer.toString(phoneNumber)), e);
            e.printStackTrace();
        } finally {
            session.close();
        }
        return userToReturn;
    }
}
