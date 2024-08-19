package org.example.moviereservationsystem.actor;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.example.moviereservationsystem.RequestNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(RequestNames.ACTORS)
public class ActorController {
    public static final String SLASH_ACTOR_SLASH = "/actor/";
    public static final String SLASH_ADD_ACTOR = "/add-actor";
    @Autowired
    private ActorService actorService;
    public static final Logger LOGGER = LoggerFactory.getLogger(ActorController.class);

    @GetMapping(SLASH_ACTOR_SLASH+"{id}")
    public ActorEntity getActor(@PathVariable int id, HttpServletResponse response) {
        ActorEntity actor = null;
        try {
            actor = actorService.getById(id);
        } catch (EntityNotFoundException e) {
            LOGGER.info("User " + id + " not found");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return actor;
    }
    @PostMapping(SLASH_ADD_ACTOR)
    public ActorEntity addActor(@RequestBody ActorEntity actor, HttpServletResponse response) {
        ActorEntity actorToReturn = null;
        try {
            actorToReturn = actorService.addActor(actor);
        }catch (EntityExistsException e){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            LOGGER.info("User " + actor.getId() + " already exists");
        } return actorToReturn;
    }
}
