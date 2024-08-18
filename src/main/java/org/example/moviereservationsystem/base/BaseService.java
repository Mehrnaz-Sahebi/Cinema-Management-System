package org.example.moviereservationsystem.base;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService {
    @Autowired
    private BaseDao baseDao;
    public <T> T getById(int id, Class<T> EntityClass) throws EntityNotFoundException {
        return baseDao.getById(id, EntityClass);
    }
    public <T> T addEntity(T entity) throws EntityExistsException {
        return baseDao.addEntity(entity);
    }
}
