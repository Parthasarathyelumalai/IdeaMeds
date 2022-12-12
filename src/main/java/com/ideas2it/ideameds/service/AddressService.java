/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.AddressDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.User;

/**
 * Interface for Address service
 *
 * @author Parthasarathy Elumalai
 * @version 1.0
 * @since 2022-11-22
 */
public interface AddressService {

    /**
     * It takes a user and an addressDTO as input, maps the addressDTO to an address object, sets the modifiedAt and
     * createdAt fields of the address object to the current date, sets the user field of the address object to the user
     * object, and saves the address object to the database
     *
     * @param user       The user object that is passed from the controller.
     * @param addressDTO This is the object that is passed from the controller.
     * @return A string - success message as response statement
     */
    String addAddress(User user, AddressDTO addressDTO);

    /**
     * It deletes the address of the user if the address is present in the user's address list
     *
     * @param user       The user object that is returned from the database.
     * @param addressDTO This is the object that contains the address details.
     * @return A string - deleted message as response statement
     */
    String deleteAddress(User user, AddressDTO addressDTO) throws CustomException;
}
