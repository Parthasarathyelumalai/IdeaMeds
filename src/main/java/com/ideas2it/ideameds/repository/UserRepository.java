/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.repository;

import com.ideas2it.ideameds.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository of User
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-18
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * find email in user table
     *
     * @param emailId - pass string as a email
     * @return User - return a user
     */
    User findByEmailId(String emailId);

    /**
     * find phone number in user table
     *
     * @param phoneNumber - pass string as a phone number
     * @return User - return a user
     */
    User findByPhoneNumber(String phoneNumber);

    /**
     * Check if a user exists with the given emailId.
     *
     * @param emailId The emailId of the user.
     * @return A boolean value if user is exit, it will return true
     *                         or else return false.
     */
    boolean existsByEmailId(String emailId);
}
