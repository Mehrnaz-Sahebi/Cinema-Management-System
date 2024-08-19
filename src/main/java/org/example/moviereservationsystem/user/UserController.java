package org.example.moviereservationsystem.user;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.example.moviereservationsystem.MessageCreator;
import org.example.moviereservationsystem.RequestNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RequestNames.USERS)
public class UserController {
    public static final String SLASH_USER_SLASH = "/user/";
    public static final String SLASH_ADD_USER = "/add-user";
    @Autowired
    private UserService userService;
    public final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping(SLASH_USER_SLASH +"{id}")
    public UserEntity getUser(@PathVariable int id, HttpServletResponse response) {
        UserEntity user = null;
        try {
            user = userService.getById(id);
        } catch (EntityNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.info(MessageCreator.notFound("User", id));
        }
        return user;
    }
    @PostMapping(SLASH_ADD_USER)
    public UserEntity addUser(@RequestBody UserEntity user, HttpServletResponse response) {
        UserEntity userToReturn = null;
        try {
            userToReturn = userService.addUser(user);
        }catch (EntityExistsException e){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            LOGGER.info(MessageCreator.alreadyExists("User", user.getId()));
        } return userToReturn;
    }
}
