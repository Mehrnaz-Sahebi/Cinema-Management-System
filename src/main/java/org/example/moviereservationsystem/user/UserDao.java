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
        if (!role.equals("ADMIN")&&!role.equals("CUSTOMER")&&!role.equals("MANAGER")) {
            throw new EntityNotFoundException("role");
        }
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        UserEntity userToReturn = null;
        try {
            transaction = session.beginTransaction();
            if (session.get(UserEntity.class, phoneNumber)==null){
                throw new EntityNotFoundException("user");
            }
            String hql1 = "UPDATE UserEntity SET role = :role WHERE id = :id";
            Query query1 = session.createQuery(hql1);
            query1.setParameter("role", role);
            query1.setParameter("id", phoneNumber);
            query1.executeUpdate();

            userToReturn = (UserEntity) session.get(UserEntity.class, phoneNumber);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(LoggerMessageCreator.errorUpdating("User", Integer.toString(phoneNumber)), e);
            e.printStackTrace();
        } finally {
            session.close();
        }
        return userToReturn;
    }
}
