package org.example.moviereservationsystem.auditorium;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditoriumService {
    @Autowired
    private AuditoriumDao auditoriumDao;
    public AuditoriumEntity addAuditorium(AuditoriumEntity auditorium) throws EntityExistsException {
        return auditoriumDao.auditorium(auditorium);
    }
    public AuditoriumEntity addAuditoriumToCinema(String auditoriumName, String cinemaName) throws EntityNotFoundException {
        return auditoriumDao.addAuditoriumToCinema(auditoriumName, cinemaName);
    }
}
