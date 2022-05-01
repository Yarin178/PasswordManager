package me.yarin.control;

import me.yarin.user.Account;
import me.yarin.user.User;
import me.yarin.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Dit is de Controller, hier worden alle POST/GET requests gestuurd via de REST API.
 */
@RestController
public class RestUserController {


    @Autowired private RestUserService userService;

    // Request v oor alle users
    @RequestMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    // Een Post request voor een "User" object aan te maken
    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public boolean createUser(@RequestBody final User user) {
        if (!userService.doesUserExist(user)) {
            if (!Utils.isEmailValid(user.getEmail())) return false;
            if (!Utils.doesPasswordMeetRequirements(user.getPassword())) return false;
            this.userService.createUser(user);
            user.setId(getUsers().size() + 1);
            return true;
        } else return false;
    }


    // Get request voor te kijken of iemand kan inloggen met de ingegeven credentials
    @RequestMapping("/users/{user}/login")
    public boolean logUserIn(@PathVariable final User user) {
        return userService.canLogIn(user.getEmail(), user.getPassword());
    }

    // Post request om een account toe te voegen aan een user
    @RequestMapping(method = RequestMethod.POST, value = "/users/{id}")
    public boolean addAccountToUser(@RequestBody final Account account, @PathVariable final int id) {
        if (userService.getUserByID(id) != null) {
            userService.addAccountEntry(userService.getUserByID(id), account);
            return true;
        } else return false;

    }

    public RestUserService getUserService() {
        return userService;
    }
}
