/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.repository.UserRepository;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service of User.
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-18
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Optional<User> addUser(User user) {
        Optional<User> savedUser = Optional.of(userRepository.save(user));

        if (savedUser.isPresent()) {
            return savedUser;
        } else {
            return Optional.empty();
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public User getUserById(Long userId) throws CustomException {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent() && (userOptional.get().getDeletedStatus() != 1)) {
            return userOptional.get();
        } else {
            throw new CustomException(Constants.USER_NOT_FOUND);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public String updateUser(User user) throws CustomException {
        User updatedUser = userRepository.save(user);

        if (null != updatedUser) {
            return updatedUser.getName() + " ." + "Updated Successfully";
        }  else {
            throw new CustomException(Constants.USER_NOT_FOUND);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public String deleteUser(User user) throws CustomException {
        Optional<User> userOptional = userRepository.findById(user.getUserId());
        if (userOptional.isPresent() && userOptional.get().getDeletedStatus() != 1) {
            user.setDeletedStatus(1);
            Optional<User> deleteUser = Optional.of(userRepository.save(user));
            if (deleteUser.isPresent()) {
                return deleteUser.get().getName() + "." + Constants.DELETED_SUCCESSFULLY;
            }
        }
        throw new CustomException(Constants.USER_NOT_FOUND);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public boolean isUserExist(Long userId) {
        return userRepository.existsById(userId);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<String> getUserPhoneNumber() {
        return userRepository.findAll().stream().map(User::getPhoneNumber).collect(Collectors.toList());
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<String> getUserEmail() {
        return userRepository.findAll().stream().map(User::getEmailId).collect(Collectors.toList());
    }
}
