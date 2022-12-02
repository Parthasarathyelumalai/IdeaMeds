/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.ResponseUserDTO;
import com.ideas2it.ideameds.dto.OrderDTO;
import com.ideas2it.ideameds.dto.UserDTO;
import com.ideas2it.ideameds.dto.UserMedicineDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.JwtRequest;
import com.ideas2it.ideameds.model.JwtResponse;
import com.ideas2it.ideameds.service.OrderService;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    private final OrderService orderService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtility jwtUtility;

    /**
     * Creates objects for the classes
     *
     * @param userService           create object for user service
     * @param userMedicineService   create object for user medicine service
     * @param orderService    create object for order system service
     * @param authenticationManager create object for authentication manager service
     * @param jwtUtility            create object for jwt utility class
     */
    @Autowired
    public UserController(UserService userService, UserMedicineService userMedicineService, OrderService orderService, AuthenticationManager authenticationManager, JwtUtility jwtUtility) {
        this.userService = userService;
        this.userMedicineService = userMedicineService;
        this.orderService = orderService;
        this.authenticationManager = authenticationManager;
        this.jwtUtility = jwtUtility;
    }

    /**
     * Add User in database
     *
     * @param user - send the user to store
     * @return user - gives a response as user details
     * @throws CustomException - occur when user's email and phone number are already registered
     */
    @PostMapping("/user")
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO user) throws CustomException {
        Optional<UserDTO> savedUser;
        if (validUserByEmailId(user.getEmailId()) && validUserByPhoneNumber(user.getPhoneNumber())) {
            throw new CustomException(Constants.EMAIL_ID_PHONE_NUMBER_EXISTS);
        } else if (validUserByEmailId(user.getEmailId())) {
            throw new CustomException(Constants.EMAIL_ID_EXISTS);
        } else if (validUserByPhoneNumber(user.getPhoneNumber())) {
            throw new CustomException(Constants.PHONE_NUMBER_EXISTS);
        } else {
            savedUser = userService.addUser(user);
            return savedUser.map(userDTO -> ResponseEntity.status(HttpStatus.OK).body(userDTO)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserDTO()));
        }
    }

    /**
     * Get a user details by id
     *
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
     *
     * @return list of user - gives a response as list of user details
     */
    @GetMapping("/user")
    public List<ResponseUserDTO> getAllUser() {
        return userService.getAllUser();
    }

    /**
     * Updated a user details
     *
     * @param user - to store an updated user details
     * @return String - give a response statement as a response
     * @throws CustomException - occur when User is not Found
     */
    @PutMapping("/user")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO user) throws CustomException {
        String updatedUser = userService.updateUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    /**
     * Delete the user in databases(Soft -delete)
     *
     * @param userId - send a user id to delete
     * @return String - give a response as
     * @throws CustomException - Occur when user is not found
     */
    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) throws CustomException {
        String deletedStatus = userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(deletedStatus);
    }

    /**
     * Get Previous User Medicine
     * @param userId - pass user id
     * @return List of UserMedicineDTO -  list of user medicine
     * @throws CustomException - Occur when user is not found
     */
    @GetMapping("/user/user-medicine/{id}")
    public ResponseEntity<List<UserMedicineDTO>> getPreviousUserMedicine(@PathVariable("id") Long userId) throws CustomException {
        if (userService.isUserExist(userId)) {
            return ResponseEntity.status(HttpStatus.OK).body(userMedicineService.getPreviousUserMedicine(userId));
        }else {
            throw new CustomException(Constants.USER_NOT_FOUND);
        }
    }

    /**
     * Add user medicines for specific user
     *
     * @param userId        - send user id to set medicines
     * @param userMedicine - send user medicine
     * @return String  - gives a response statement
     * @throws CustomException - occur when User is not Found
     */
    @PostMapping("/user/user-medicine/{id}")
    public ResponseEntity<String> addUserMedicine(@PathVariable("id") Long userId, @Valid @RequestBody UserMedicineDTO userMedicine) throws CustomException {
        boolean isUserExist = userService.isUserExist(userId);
        Long savedCartId;
        if (isUserExist) {
            savedCartId = userMedicineService.addUserMedicine(userId,userMedicine);
            if ( savedCartId != null ) {
                return ResponseEntity.status(HttpStatus.OK).body(Constants.ADDED_TO_CART);
            } else {
                throw new CustomException(Constants.CAN_NOT_ADD_ITEMS_IN_CART);
            }
        } else {
            throw new CustomException(Constants.USER_NOT_FOUND);
        }
    }

    /**
     * Get a user previous order details
     *
     * @param userId - send user id
     * @return list of order - gives response as list of order by user
     * @throws CustomException - occur when there is no order history
     */
    @GetMapping("/user/order/{id}")
    public ResponseEntity<List<OrderDTO>> getUserPreviousOrder(@PathVariable("id") Long userId) throws CustomException {
        Optional<List<OrderDTO>> savedOrders =  orderService.getOrderByUserId(userId);
        if(savedOrders.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(savedOrders.get());
        }
        throw new CustomException(Constants.NO_HISTORY_OF_ORDERS);
    }

    /**
     * To valid User By phone number
     *
     * @param userPhoneNumber - send a user Email id to validate
     * @return boolean - true or false
     */
    private boolean validUserByPhoneNumber(String userPhoneNumber) {
        List<String> userPhoneNumbers = userService.getUserPhoneNumber();
        for (String number : userPhoneNumbers) {
            if (number.equals(userPhoneNumber)) {
                return true;
            }
        }
        return false;
    }

    /**
     * To valid User By EmailId
     *
     * @param userEmailId - send a user Email id to validate
     * @return boolean - true or false
     */
    private boolean validUserByEmailId(String userEmailId) {
        List<String> userEmailIds = userService.getUserEmail();
        for (String emailId : userEmailIds) {
            if (userEmailId.equals(emailId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Authentication request using jwt token
     *
     * @param jwtRequest - get a username and password
     * @return JwtResponse - send a response as token
     */
    @PostMapping("/authenticate")
    public JwtResponse authenticate(@Valid @RequestBody JwtRequest jwtRequest) throws CustomException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUserName(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException exception) {
            throw new CustomException(Constants.INVALID_CREDENTIALS);
        }
        final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUserName());
        final String token = jwtUtility.generateToken(userDetails);

        return new JwtResponse(token);
    }
}
