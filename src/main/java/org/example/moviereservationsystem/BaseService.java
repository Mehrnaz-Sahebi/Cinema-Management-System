package org.example.moviereservationsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService {
    @Autowired
    private BaseDao baseDao;
    public <T> T getById(int id, Class<T> EntityClass) {
        return baseDao.getById(id, EntityClass);
    }
}
