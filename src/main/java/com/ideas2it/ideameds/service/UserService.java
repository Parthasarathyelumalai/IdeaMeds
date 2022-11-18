package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.User;

import java.util.List;

/**
 * Interface for UserService
 *
 * @author - Parthasarathy Elumalai
 * @since - 2022-11-18
 * @version - 1.0
 */
public interface UserService {

    User addUser(User user);

    User getUser(Long userId);

    List<User> getAllUser();

    String updateUser(User user);

    String deleteUser(Long userId);

}
