package org.example.moviereservationsystem.admin;

import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletResponse;
import org.example.moviereservationsystem.RequestNames;
import org.example.moviereservationsystem.address.AddressEntity;
import org.example.moviereservationsystem.cinema.CinemaDto;
import org.example.moviereservationsystem.cinema.CinemaEntity;
import org.example.moviereservationsystem.cinema.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RequestNames.ADMIN)
public class AdminController {
    @Autowired
    private CinemaService cinemaService;
    @PostMapping("/add-cinema")
    public CinemaEntity addCinema(@RequestBody CinemaDto cinema, HttpServletResponse response) {
        System.out.println("hello");
        CinemaEntity cinemaToReturn = null;
        AddressEntity address = new AddressEntity(cinema.getAddressId(),cinema.getNumber(),cinema.getAlley(),cinema.getStreet(),cinema.getCity());
        CinemaEntity cinemaToSave = new CinemaEntity(0,cinema.getName(),address,null,null);
        try {
            cinemaToReturn = cinemaService.addCinema(cinemaToSave);
        } catch (EntityExistsException e) {

        }
        return cinemaToReturn;
    }
}
