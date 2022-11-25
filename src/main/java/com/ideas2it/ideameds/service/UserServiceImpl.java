/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.AddressDTO;
import com.ideas2it.ideameds.dto.UserDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.Address;
import com.ideas2it.ideameds.repository.UserRepository;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.util.Constants;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service of User.
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-18
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     *{@inheritDoc}
     */
    @Override
    public Optional<UserDTO> addUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);;
        List<Address> addresses = user.getAddresses();
        addresses.removeAll(user.getAddresses());
        for(AddressDTO addressDTO : userDTO.getAddresses()) {
            addresses.add(modelMapper.map(addressDTO,Address.class));
        }
        return Optional.of(modelMapper.map(userRepository.save(user), UserDTO.class));
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public UserDTO getUserById(Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);
        UserDTO fetchedUser = modelMapper.map(user,UserDTO.class);

        if (fetchedUser != null && (fetchedUser.getDeletedStatus() != 1)) {
            return fetchedUser;
        } else {
            throw new CustomException(Constants.USER_NOT_FOUND);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<UserDTO> getAllUser() {
        List<UserDTO> users = new ArrayList<>();
        for (User user: userRepository.findAll()) {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            users.add(userDTO);
        }
        return users;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public String updateUser(UserDTO userDTO) throws CustomException {
        User user = modelMapper.map(userDTO, User.class);
        UserDTO updatedUser = modelMapper.map(userRepository.save(user), UserDTO.class);

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
    public String deleteUser(UserDTO userDTO) throws CustomException {
        User user = modelMapper.map(userDTO, User.class);
        Optional<User> userOptional = userRepository.findById(user.getUserId());
        if (userOptional.isPresent() && userOptional.get().getDeletedStatus() != 1) {
            user.setDeletedStatus(1);
            Optional<UserDTO> deleteUser = Optional.of(modelMapper.map(userRepository.save(user),UserDTO.class));
            return deleteUser.get().getName() + "." + Constants.DELETED_SUCCESSFULLY;
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
        return userRepository.findAll().stream().map(User::getPhoneNumber).toList();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<String> getUserEmail() {
        return userRepository.findAll().stream().map(User::getEmailId).toList();
    }
}
