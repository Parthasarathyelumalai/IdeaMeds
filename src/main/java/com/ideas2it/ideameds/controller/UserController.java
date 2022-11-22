package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.exception.UserException;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.model.UserMedicine;
import com.ideas2it.ideameds.service.UserMedicineService;
import com.ideas2it.ideameds.service.UserService;
import lombok.RequiredArgsConstructor;
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
 * @version - 1.0
 * @since - 2022-11-18
 */
@RestController
@RequestMapping
@Slf4j
public class UserController {

    private UserService userService;

    private UserMedicineService userMedicineService;


    @Autowired
    public UserController(UserService userService, UserMedicineService userMedicineService) {
        this.userService = userService;
        this.userMedicineService = userMedicineService;
    }

    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        Optional<User> savedUser = userService.addUser(user);

        if ( savedUser.isPresent() ) {
            return ResponseEntity.status(HttpStatus.OK).body(savedUser.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new User());
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long userId) {
        Optional<User> fetchedUser = userService.getUserById(userId);

        if ( fetchedUser.isPresent() ) {
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
        Optional<String> updateUser = userService.updateUser(user);

        if ( updateUser.isPresent() ) {
            return ResponseEntity.status(HttpStatus.OK).body(updateUser.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("there is no User under this id");
        }
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestBody User user) {
        user.setDeletedStatus(1);
        Optional<String> deletedStatus = userService.deleteUser(user);

        if ( deletedStatus.isPresent() ) {
            return ResponseEntity.status(HttpStatus.OK).body(deletedStatus.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("there is no User under this id");
        }
    }

    @PostMapping("/user/user-medicine/{id}")
    public ResponseEntity<List<UserMedicine>> addUserMedicine(@PathVariable("id") Long userId, @RequestBody List<UserMedicine> userMedicines) throws UserException {
        boolean isUserExist = userService.isUserExist(userId);
        Optional<List<UserMedicine>> savedUserMedicines = null;
        if ( isUserExist ) {
            savedUserMedicines = userMedicineService.addUserMedicine(userMedicines);
        } else {
            throw new UserException("there is no user under this id");
        }
        return ResponseEntity.status(HttpStatus.OK).body(savedUserMedicines.get());
    }
}
