/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.OrderSystem;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.model.UserMedicine;
import com.ideas2it.ideameds.service.OrderSystemService;
import com.ideas2it.ideameds.service.UserMedicineService;
import com.ideas2it.ideameds.service.UserService;
import com.ideas2it.ideameds.util.Constants;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserMedicineService userMedicineService;
    private final OrderSystemService orderSystemService;

    public UserController(UserService userService, UserMedicineService userMedicineService, OrderSystemService orderSystemService) {
        this.userService = userService;
        this.userMedicineService = userMedicineService;
        this.orderSystemService = orderSystemService;
    }

    /**
     * Add User in database
     * @param user - send the user to store
     * @return user - gives a response as user details
     * @throws CustomException - occur when user's email and phone number are already registered
     */
    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody User user) throws CustomException {
        Optional<User> savedUser;
        if (validUserByEmailId(user.getEmailId()) && validUserByPhoneNumber(user.getPhoneNumber())  ) {
            throw new CustomException(Constants.EMAIL_ID_PHONE_NUMBER_EXISTS);
        } else if ( validUserByEmailId(user.getEmailId()) ) {
            throw new CustomException(Constants.EMAIL_ID_EXISTS);
        } else if ( validUserByPhoneNumber(user.getPhoneNumber()) ) {
            throw new CustomException(Constants.PHONE_NUMBER_EXISTS);
        } else {
            savedUser = userService.addUser(user);
            if ( savedUser.isPresent() ) {
                return ResponseEntity.status(HttpStatus.OK).body(savedUser.get());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new User());
            }
        }
    }

    /**
     * Get a user details by id
     * @param userId - send the user id
     * @return user - give response as user details
     * @throws CustomException - occur when User is not Found
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long userId) throws CustomException {
        User fetchedUser = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(fetchedUser);
    }

    /**
     * Get list of user details
     * @return list of user - gives a response as list of user details
     */
    @GetMapping("/user")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    /**
     * Updated a user details
     * @param user - to store an updated user details
     * @return String - give a response statement as a response
     * @throws CustomException - occur when User is not Found
     */
    @PutMapping("/user")
    public ResponseEntity<String> updateUser(@RequestBody User user) throws CustomException {
        String updateUser = userService.updateUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(updateUser);
    }

    /**
     * Delete the user in databases(Soft -delete)
     * @param user - send a user to delete
     * @return String - give a response as
     * @throws CustomException - throw an error message
     */
    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestBody User user) throws CustomException {
        String deletedStatus = userService.deleteUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(deletedStatus);
    }

    /**
     * Add user medicines for specific user
     * @param userId - send user id to set medicines
     * @param userMedicines - send user medicines
     * @return list of medicine - gives a response as list of user medicines
     * @throws CustomException - occur when User is not Found
     */
    @PostMapping("/user/user-medicine/{id}")
    public ResponseEntity<List<UserMedicine>> addUserMedicine(@PathVariable("id") Long userId, @RequestBody List<UserMedicine> userMedicines) throws CustomException {
        boolean isUserExist = userService.isUserExist(userId);
        Optional<List<UserMedicine>> savedUserMedicines;
        if ( isUserExist ) {
            savedUserMedicines = userMedicineService.addUserMedicine(userMedicines);
        } else {
            throw new CustomException(Constants.USER_NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(savedUserMedicines.get());
    }

    /**
     * Get a user previous order details
     * @param userId - send user id
     * @return list of order - gives response as list of order by user
     */
    @GetMapping("/user/user-medicine/{id}")
    public List<OrderSystem> getUserPreviousOrder(@PathVariable("id") Long userId) {
        return orderSystemService. getUserPreviousOrder(userId);
    }

    /**
     * To valid User By phone number
     * @param userPhoneNumber - send a user Email id to validate
     * @return boolean - true or false
     */
    private boolean validUserByPhoneNumber(String userPhoneNumber) {
        List<String> userPhoneNumbers = userService.getUserPhoneNumber();
        List<String> userEmailIds = userService.getUserEmail();
        for (String number : userPhoneNumbers) {
            if ( number.equals(userPhoneNumber)) {
                return true;
            }
        }
        return false;
    }

    /**
     * To valid User By EmailId
     * @param userEmailId - send a user Email id to validate
     * @return boolean - true or false
     */
    private boolean validUserByEmailId(String userEmailId) {
        List<String> userEmailIds = userService.getUserEmail();
        for (String emailId : userEmailIds) {
            if ( userEmailId.equals(emailId)) {
                return true;
            }
        }
        return false;
    }
}
