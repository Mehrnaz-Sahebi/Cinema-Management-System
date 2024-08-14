package org.example.moviereservationsystem;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;

import java.util.function.Supplier;

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

    public <T> T createInstance(Supplier<T> supplier) {
        return supplier.get();
    }


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
            e.printStackTrace();
        } finally {
            session.close();
        }
        if (t==null) throw new EntityNotFoundException();
        return t;
    }
    public <T> T addEntity(T entity) throws EntityExistsException {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        T entityToReturn = null;
        try {
            transaction = session.beginTransaction();
            //TODO replace save
            entityToReturn = (T) session.save(entity);
            transaction.commit();
        } catch (HibernateException e){
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } catch (EntityExistsException e){
            throw new EntityExistsException();
        } finally {
            session.close();
        }
        return entityToReturn;
    }
}
