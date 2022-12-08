/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.ResponseUserDTO;
import com.ideas2it.ideameds.dto.UserDTO;
import com.ideas2it.ideameds.exception.CustomException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

/**
 * Interface for UserService
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-18
 */
public interface UserService {

    /**
     * Customer or Admin details store in the database
     * before that check the user emailId and phone number must be unique in database.
     * if emailId and phone already exist in table, it will show error message.
     *
     * @param userDTO - pass user details to store the user in the databases
     * @return user - after store in database to give a response as user details.
     * @throws CustomException - occur when user email id and phone is already existed in the database.
     */
    Optional<UserDTO> addUser(UserDTO userDTO) throws CustomException;

    /**
     * Get a details of individual user from databases
     * if user aren't exist, it will pass user not found message.
     *
     * @param userId - gives a userId to get corresponded user details
     * @return user - gives a response as a user details
     * @throws CustomException - occur when user mail or phone is exist or occur user is not saved
     */
    ResponseUserDTO getUserById(Long userId) throws CustomException;

    /**
     * Get details of all user registration details in databases
     * before that check deleted status of user table should be false.
     *
     * @return list of user - gives a response of all user details.
     */
    List<ResponseUserDTO> getAllUser();

    /**
     * Updates the user details in the database. if user update the emailId and phone number,
     * it is necessary to check emailId and phone number shouldn't already exist.
     *
     * @param user - send a updated user details to update on database
     * @return Statement for updated progress - give a response statement
     * @throws CustomException - occur when user is not found and
     *                         occur when user update the email id or phone is already existed.
     */
    String updateUser(UserDTO user) throws CustomException;

    /**
     * delete the  user details in the database. here it will softly delete the user details.
     * (update deleted status of user : true)
     *
     * @param userId - set user(Which contains deleted status = true)
     * @return Statement for deleted progress - give a response statement as response
     * @throws CustomException - occur when user is not found in the progress of deletion.
     */
    String deleteUser(Long userId) throws CustomException;

    /**
     * This function returns true if the user with the given userId exists in the database, false otherwise.
     *
     * @param userId -  pass userId to check user is exit or not
     * @return if user exit - true
     * if not - false
     */
    boolean isUserExist(Long userId);

    /**
     * Given a username, return a UserDetails object that represents the user.
     * The UserDetails object is a Spring Security interface that represents a user. It has a number of methods that return
     * information about the user
     *
     * @param userName The username of the user to load user details.
     * @return A UserDetails object  - Send userDetails to authenticate the user.
     */
    UserDetails loadUserByUsername(String userName);
}
