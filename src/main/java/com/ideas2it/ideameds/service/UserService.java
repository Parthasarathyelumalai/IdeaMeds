package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface for UserService
 *
 * @author - Parthasarathy Elumalai
 * @since - 2022-11-18
 * @version - 1.0
 */
public interface UserService {

    Optional<User> addUser(User user);

    Optional<User> getUser(Long userId);

    List<User> getAllUser();

    Optional<String> updateUser(User user);

    Optional<String> deleteUser(Long userId);

}
