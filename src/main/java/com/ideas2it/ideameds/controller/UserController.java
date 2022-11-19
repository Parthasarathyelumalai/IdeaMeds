package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for User
 *
 * @author - Parthasarathy Elumalai
 * @since - 2022-11-18
 * @version - 1.0
 */
@RestController
@RequestMapping
@Slf4j
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        Optional<User> savedUser = userService.addUser(user);
        if (savedUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(savedUser.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new User());
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long userId) {
        Optional<User> fetchedUser = userService.getUser(userId);
        if (fetchedUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(fetchedUser.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new User());
        }
    }

    @GetMapping("/user")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @PutMapping("/user")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        Optional<String > updateUser = userService.updateUser(user);
        if (updateUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(updateUser.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("there is no User under this id");
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {

        Optional<String > deletedStatus = userService.deleteUser(userId);
        if (deletedStatus.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(deletedStatus.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("there is no User under this id");
        }
    }
}
