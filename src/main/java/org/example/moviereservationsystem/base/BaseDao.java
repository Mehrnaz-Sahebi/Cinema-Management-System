package org.example.moviereservationsystem.base;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import org.example.moviereservationsystem.LoggerMessageCreator;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@EnableTransactionManagement
@Transactional
public class BaseDao {
    @Getter
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;
    @Getter
    private SessionFactory sessionFactory;
    private EntityManager entityManager;

    @PersistenceContext(name = "entityManagerFactory")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    public void setEntityManagerFactory(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        sessionFactory = entityManagerFactory.getNativeEntityManagerFactory().unwrap(SessionFactory.class);
    }

    public static final Logger LOGGER = LoggerFactory.getLogger(BaseDao.class);

    public <T> T getById(int id, Class<T> entityClass) throws EntityNotFoundException {
        Session session = getSession();
        T t = null;
        try {
            t = (T) session.get(entityClass, id);
            if (t == null) {
                throw new EntityNotFoundException();
            }
        } catch (HibernateException e) {
            LOGGER.error(LoggerMessageCreator.errorGetting(entityClass.getSimpleName(),id),e);
        }
        return t;
    }


    public <T extends BaseEntity> T addEntity(T entity) throws EntityExistsException {
        Session session = getSession();
        try {
            if (session.get(entity.getClass(), entity.getId()) != null) {
                throw new EntityExistsException();
            }
            session.persist(entity);
        }catch (HibernateException e) {
            LOGGER.error(LoggerMessageCreator.errorCreating(entity.getClass().getSimpleName(),entity.getId()),e);
        }
        return entity;
    }

    public <T extends BaseEntity> void deleteEntity(String fieldName, String fieldValue, Boolean isInt, Class<T> entityClass) throws EntityNotFoundException {
        Session session = getSession();
        try {
            String hql1 = "FROM " + entityClass.getSimpleName() + " E WHERE E." + fieldName + " =: fieldValue";
            Query query1 = session.createQuery(hql1);
            if (isInt) {
                query1.setParameter("fieldValue", Integer.parseInt(fieldValue));
            } else {
                query1.setParameter("fieldValue", fieldValue);
            }
            List results1 = query1.list();
            if (results1.isEmpty()) {
                throw new EntityNotFoundException();
            }
            String hql = "delete from " + entityClass.getSimpleName() + " E where E." + fieldName + " =: fieldValue";
            Query query = session.createQuery(hql);
            query.setParameter("fieldValue", fieldValue);
            query.executeUpdate();
        } catch (HibernateException e) {
            LOGGER.error(LoggerMessageCreator.errorDeleting(entityClass.getSimpleName(),fieldValue),e);
        }
    }

    protected Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
