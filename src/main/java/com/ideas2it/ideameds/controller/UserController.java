/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.UserDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.JwtRequest;
import com.ideas2it.ideameds.model.JwtResponse;
import com.ideas2it.ideameds.model.OrderSystem;
import com.ideas2it.ideameds.model.UserMedicine;
import com.ideas2it.ideameds.service.OrderSystemService;
import com.ideas2it.ideameds.service.UserMedicineService;
import com.ideas2it.ideameds.service.UserService;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.JwtUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.DispatcherType;
import javax.servlet.annotation.WebFilter;
import java.util.ArrayList;
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
@WebFilter(urlPatterns = {"/user"})
public class UserController {
    private final UserService userService;
    private final UserMedicineService userMedicineService;
    private final OrderSystemService orderSystemService;
    private AuthenticationManager authenticationManager;

    private JwtUtility jwtUtility;
    public UserController(UserService userService, UserMedicineService userMedicineService, OrderSystemService orderSystemService,AuthenticationManager authenticationManager, JwtUtility jwtUtility) {
        this.userService = userService;
        this.userMedicineService = userMedicineService;
        this.orderSystemService = orderSystemService;
        this.authenticationManager = authenticationManager;
        this.jwtUtility = jwtUtility;
    }

    /**
     * Add User in database
     * @param user - send the user to store
     * @return user - gives a response as user details
     * @throws CustomException - occur when user's email and phone number are already registered
     */
    @PostMapping("/user")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO user) throws CustomException {
        Optional<UserDTO> savedUser;
        if (validUserByEmailId(user.getEmailId()) && validUserByPhoneNumber(user.getPhoneNumber())  ) {
            throw new CustomException(Constants.EMAIL_ID_PHONE_NUMBER_EXISTS);
        } else if ( validUserByEmailId(user.getEmailId()) ) {
            throw new CustomException(Constants.EMAIL_ID_EXISTS);
        } else if ( validUserByPhoneNumber(user.getPhoneNumber()) ) {
            throw new CustomException(Constants.PHONE_NUMBER_EXISTS);
        } else {
            savedUser = userService.addUser(user);
            return savedUser.map(userDTO -> ResponseEntity.status(HttpStatus.OK).body(userDTO)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserDTO()));
        }
    }

    /**
     * Get a user details by id
     * @param userId - send the user id
     * @return user - give response as user details
     * @throws CustomException - occur when User is not Found
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long userId) throws CustomException {
        UserDTO fetchedUser = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(fetchedUser);
    }

    /**
     * Get list of user details
     * @return list of user - gives a response as list of user details
     */
    @GetMapping("/user")
    public List<UserDTO> getAllUser() {
        return userService.getAllUser();
    }

    /**
     * Updated a user details
     * @param user - to store an updated user details
     * @return String - give a response statement as a response
     * @throws CustomException - occur when User is not Found
     */
    @PutMapping("/user")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO user) throws CustomException {
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
    public ResponseEntity<String> deleteUser(@RequestBody UserDTO user) throws CustomException {
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
        return ResponseEntity.status(HttpStatus.OK).body(savedUserMedicines.orElse(null));
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

    /**
     * Authentication request using jwt token
     *
     * @param jwtRequest -
     * @return JwtResponse -
     * @throws Exception -
     */
    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
        log.info("Inside authenticate");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch ( BadCredentialsException exception ) {
            throw new Exception("Invalid_credentials");
        }

        final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
        log.info(String.valueOf(userDetails));
        log.info(" final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());");
        final String token = jwtUtility.generateToken(userDetails);

        return new JwtResponse(token);
    }
}
