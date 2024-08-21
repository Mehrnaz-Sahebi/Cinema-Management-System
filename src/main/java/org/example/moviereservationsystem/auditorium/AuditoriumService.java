package org.example.moviereservationsystem.auditorium;

import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditoriumService {
    @Autowired
    private AuditoriumDao auditoriumDao;
    public AuditoriumEntity addAuditorium(AuditoriumEntity auditorium) throws EntityExistsException {
        return auditoriumDao.auditorium(auditorium);
    }
}
