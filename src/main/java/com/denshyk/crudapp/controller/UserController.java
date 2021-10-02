package com.denshyk.crudapp.controller;

import com.denshyk.crudapp.exception.ResourceNotFoundException;
import com.denshyk.crudapp.model.User;
import com.denshyk.crudapp.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/users")
    public User createUser(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping(value = "/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/users/{id}")
    public User getUserById(@PathVariable("id") @Min(1) Long id) {
        return userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with " + id + " is Not Found!"));
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable("id") @Min(1) Long id, @RequestBody User newUser){
        User user = userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with " + id + " is Not Found!"));
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        userService.saveUser(user);
        return "The data was updated.";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") @Min(1) Long id) {
        User user = userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with " + id + " is Not Found!"));
        userService.deleteById(user.getId());
        return "Student with ID :" + id + " is deleted";
    }
}