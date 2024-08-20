package org.example.moviereservationsystem.base;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import org.example.moviereservationsystem.LoggerMessageCreator;
import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDao {
    @Getter
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;
    @Getter
    private SessionFactory sessionFactory;
    @Autowired
    public void setEntityManagerFactory(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        sessionFactory = entityManagerFactory.getNativeEntityManagerFactory().unwrap(SessionFactory.class);
    }
    public static final Logger LOGGER = LoggerFactory.getLogger(BaseDao.class);
    //TODO CAN YOU DO SMTH ABOUT THE LISTS?
    public <T> T getById(int id, Class<T> entityClass) throws EntityNotFoundException{
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        T t = null;
        try {
            transaction = session.beginTransaction();
            t = (T) session.get(entityClass, id);
            transaction.commit();
        } catch (HibernateException e){
            if (transaction != null) transaction.rollback();
            LOGGER.error(LoggerMessageCreator.errorGetting(entityClass.getSimpleName(),id), e);
        } finally {
            session.close();
        }
        if (t==null) throw new EntityNotFoundException();
        return t;
    }
    public <T extends BaseEntity> T addEntity(T entity) throws EntityExistsException {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (session.get(entity.getClass(), entity.getId()) != null) {
                throw new EntityExistsException();
            }
            //TODO replace save
            session.persist(entity);
            transaction.commit();
        }
        catch (HibernateException e){
            if (transaction != null) transaction.rollback();
            LOGGER.error(LoggerMessageCreator.errorCreating(entity.getClass().getSimpleName(),entity.getId()), e);
            return null;//TODO be careful
        }
        finally{
            session.close();
        }
        return entity;
    }
}
