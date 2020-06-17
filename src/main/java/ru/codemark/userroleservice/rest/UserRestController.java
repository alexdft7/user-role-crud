package ru.codemark.userroleservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.codemark.userroleservice.entity.User;
import ru.codemark.userroleservice.helper.PasswordValidityCheck;
import ru.codemark.userroleservice.service.UserService;

import java.util.List;

@RestController
public class UserRestController {

    private UserService userService;
    private PasswordValidityCheck passwordValidityCheck;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService=userService;
    }

    @GetMapping("/get/{id}")
    public User getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    @GetMapping("/list")
    public List<User> getBooks() {
        return userService.getUsers();
    }

    @PostMapping("/add")
    public String addUser(@RequestBody User user) {
        passwordValidityCheck = new PasswordValidityCheck();
        if (formIsLegit(user)) {
            userService.saveUser(user);
            return "{success: true}";
        }
        else return "{success: false, errors: {" + passwordValidityCheck.getErrorMessage(user) + "}}";
    }

    @PutMapping("/edit")
    public String updateUser(@RequestBody User user) {
        passwordValidityCheck = new PasswordValidityCheck();
        if (formIsLegit(user)) {
            userService.updateUser(user);
            return "{success: true}";
        }
        else return "{success: false, errors: {" + passwordValidityCheck.getErrorMessage(user) + "}}";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        userService.deleteUser(id);
        return "{result: you've deleted user with id " + id + ".}";
    }

    private Boolean formIsLegit(User user) {
        return user.getName().length() > 0 && user.getLogin().length() > 0 && passwordValidityCheck.passed(user.getPassword());
    }
}