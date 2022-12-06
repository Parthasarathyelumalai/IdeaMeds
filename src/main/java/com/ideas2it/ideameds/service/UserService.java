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
     * Add user in database
     *
     * @param userDTO - To store the user in databases
     * @return user - To give a response as user details
     */
    Optional<UserDTO> addUser(UserDTO userDTO) throws CustomException;

    /**
     * Get User by id from the database
     *
     * @param userId - give a userId to get data
     * @return user - give a response as a user details
     * @throws CustomException - occur when user mail or phone is exist or occur user is not saved
     */
    UserDTO getUserById(Long userId) throws CustomException;

    /**
     * Get Users details from the database
     *
     * @return user - give a response as a users details
     */
    List<ResponseUserDTO> getAllUser();

    /**
     * Updated user details in databases
     *
     * @param user - send a updated user details to update on database
     * @return String - give a response statement as response
     * @throws CustomException - occur when user is not found and occur when user mail or phone is exist
     */
    String updateUser(UserDTO user) throws CustomException;

    /**
     * deleted user details in databases
     *
     * @param userId - set user(Which contains deleted status = true)
     * @return String - give a response statement as response
     * @throws CustomException - occur when user is not found
     */
    String deleteUser(Long userId) throws CustomException;

    /**
     * Check the user is exists or not
     *
     * @param userId - pass userId to check
     * @return boolean - true or false
     */
    boolean isUserExist(Long userId);

    /**
     * get UserDetails by username(EmailId)
     *
     * @param userName - pass username
     * @return userDetails - Send userDetails
     */
    UserDetails loadUserByUsername(String userName);
}
