/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.OrderDTO;
import com.ideas2it.ideameds.dto.ResponseUserDTO;
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
     * @param orderService          create object for order system service
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
     * It takes a UserDTO object as a request body, validates it, and then calls the addUser function in the userService.
     * If the user is added, it returns the user object, else it throws a custom exception
     *
     * @param user The user object that is to be added.
     * @return ResponseEntity<UserDTO> - gives a response as user details
     * @throws CustomException - occur when user's email and phone number are already registered
     */
    @PostMapping("/user")
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO user) throws CustomException {
        Optional<UserDTO> savedUser = userService.addUser(user);
        if ( savedUser.isPresent() ) {
            return ResponseEntity.status(HttpStatus.OK).body(savedUser.get());
        }
        throw new CustomException(HttpStatus.NO_CONTENT, Constants.USER_NOT_ADDED);
    }

    /**
     * It fetches a user by id and returns a response entity with the fetched user
     * if user is not present, it will throw error message (User not found)
     *
     * @param userId The id of the user to be fetched.
     * @return ResponseEntity<ResponseUserDTO>  - give response as user details
     * @throws CustomException - occur when User is not Found
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<ResponseUserDTO> getUserById(@PathVariable("id") Long userId) throws CustomException {
        ResponseUserDTO fetchedUser = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(fetchedUser);
    }

    /**
     * It returns a list of all users.
     * if user table is empty, it will show empty list of user.
     *
     * @return A list of ResponseUserDTO objects  - gives a response as list of user details.
     */
    @GetMapping("/user")
    public List<ResponseUserDTO> getAllUser() {
        return userService.getAllUser();
    }

    /**
     * It takes a UserDTO object as a parameter, calls the updateUser function in the userService class, and returns a
     * ResponseEntity object with the updated user
     *
     * @param userDTO The user object that needs to be updated.
     * @return A ResponseEntity object is being returned - gives update response statement.
     * @throws CustomException - occur when User is not Found and
     *                         also validate the email id and phone number before update it,
     *                         if it exists, it will throw the error message.
     */
    @PutMapping("/user")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO) throws CustomException {
        String updatedUser = userService.updateUser(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }


    /**
     * It deletes a user from the database and returns a response entity with a status code of 200 and a body of the
     * deleted status
     * Note: In the delete progress, user's deleted status update into true.
     *
     * @param userId The id of the user to be deleted.
     * @return ResponseEntity<String> - give a response as statement for delete user.
     * @throws CustomException - Occur when user is not found
     */
    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) throws CustomException {
        String deletedStatus = userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(deletedStatus);
    }

    /**
     * It returns a list of UserMedicineDTO objects for a given userId
     * Get usage history of user medicine. if medicines presents, it will show the list of medicine
     * otherwise it will show empty list.
     *
     * @param userId The id of the user whose previous medicine is to be fetched.
     * @return A list of UserMedicineDTO objects  -  list of user medicine.
     * @throws CustomException - Occur when user is not found
     */
    @GetMapping("/user/user-medicine/{id}")
    public ResponseEntity<List<UserMedicineDTO>> getPreviousUserMedicine(@PathVariable("id") Long userId) throws CustomException {
        if ( userService.isUserExist(userId) ) {
            return ResponseEntity.status(HttpStatus.OK).body(userMedicineService.getPreviousUserMedicine(userId));
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
        }
    }

    /**
     * It adds a medicine to the cart of a user
     * And check if  medicine is exists, it will add to cart.
     * if not , it will throw error message(medicine not found).
     *
     * @param userId       The id of the user to whom the medicine is to be added.
     * @param userMedicine This is the object that will be passed in the request body.
     * @return ResponseEntity<String>  - gives a response statement
     * @throws CustomException - occur when User is not Found
     *                         occur when User medicine is not added
     */
    @PostMapping("/user/user-medicine/{id}")
    public ResponseEntity<String> addUserMedicine(@PathVariable("id") Long userId, @Valid @RequestBody UserMedicineDTO userMedicine) throws CustomException {
        boolean isUserExist = userService.isUserExist(userId);
        Long savedCartId;
        if ( isUserExist ) {
            savedCartId = userMedicineService.addUserMedicine(userId, userMedicine);
            if ( savedCartId != null ) {
                return ResponseEntity.status(HttpStatus.OK).body(Constants.ADDED_TO_CART);
            } else {
                throw new CustomException(HttpStatus.NO_CONTENT, Constants.CAN_NOT_ADD_ITEMS_IN_CART);
            }
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
        }
    }

    /**
     * It returns a list of orders for a user with the given userId
     *
     * @param userId The userId of the user whose order history is to be fetched.
     * @return A list of OrderDTO objects  - gives response as list of order by user.
     * @throws CustomException - occur when there is no order history
     */
    @GetMapping("/user/order/{id}")
    public ResponseEntity<List<OrderDTO>> getUserPreviousOrder(@PathVariable("id") Long userId) throws CustomException {
        Optional<List<OrderDTO>> savedOrders = orderService.getOrderByUserId(userId);
        if ( savedOrders.isPresent() ) {
            return ResponseEntity.status(HttpStatus.OK).body(savedOrders.get());
        }
        throw new CustomException(HttpStatus.NOT_FOUND, Constants.NO_HISTORY_OF_ORDERS);
    }

    /**
     * It takes a JwtRequest object as input, validates it, and returns a JwtResponse object
     *
     * @param jwtRequest This is the request object that contains the username and password.
     * @return A JWT token - send a response as token
     * @throws CustomException - occur when unauthorized user login in
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
        } catch ( BadCredentialsException exception ) {
            throw new CustomException(HttpStatus.NOT_ACCEPTABLE, Constants.INVALID_CREDENTIALS);
        }
        final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUserName());
        final String token = jwtUtility.generateToken(userDetails);

        return new JwtResponse(token);
    }
}
