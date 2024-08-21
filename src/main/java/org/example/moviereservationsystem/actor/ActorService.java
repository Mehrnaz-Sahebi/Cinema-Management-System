package org.example.moviereservationsystem.actor;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.example.moviereservationsystem.base.BaseService;
import org.example.moviereservationsystem.movie.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActorService extends BaseService {
    @Autowired
    private ActorDao actorDao;

    public ActorEntity getById(int id) throws EntityNotFoundException {
        return actorDao.getById(id);
    }
    public ActorEntity addActor(ActorEntity actor) throws EntityExistsException {
        return actorDao.addActor(actor);
    }
    public void deleteActor(String firstName, String LastName) throws EntityNotFoundException{
        actorDao.deleteActor(firstName, LastName);
    }
}
