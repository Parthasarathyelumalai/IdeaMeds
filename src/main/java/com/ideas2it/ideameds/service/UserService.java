/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.AddressDTO;
import com.ideas2it.ideameds.dto.ResponseUserDTO;
import com.ideas2it.ideameds.dto.UserDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.User;
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
     * @return userDTO - gives a response as a user DTO details
     * @throws CustomException - occur when user is not found
     */
    ResponseUserDTO getUserDTOById(Long userId) throws CustomException;

    /**
     * Get a details of individual user from databases
     * if user aren't exist, it will pass user not found message.
     *
     * @param userId - gives a userId to get corresponded user details
     * @return user - gives a response as a user details
     * @throws CustomException - occur when user is not found
     */
    User getUserById(Long userId) throws CustomException;

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

    /**
     * It adds an address to a user
     *
     * @param userId     The userId of the user who is adding the address.
     * @param addressDTO This is the object that contains the address details.
     * @return String - gives a response statement as success
     * @throws CustomException - occur when user is not found
     */
    String addUserAddress(Long userId, AddressDTO addressDTO) throws CustomException;

    /**
     * Delete an address for a user
     *
     * @param userId     The userId of the user whose address is to be deleted.
     * @param addressDTO This is the address object that you want to delete.
     * @return String - gives a response statement as deleted
     * @throws CustomException - occur when user is not found,
     *                         occur when user's address is not found
     */
    String deleteUserAddress(Long userId, AddressDTO addressDTO) throws CustomException;

    /**
     * This function returns true if the emailId is a valid user name, and false otherwise.
     *
     * @param emailId The email id of the user.
     * @return boolean true or false - if user is exit, it will return true
     *                                 or else return false.
     */
    boolean isValidUserName(String emailId);
}
