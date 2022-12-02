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
import com.ideas2it.ideameds.security.CustomUserDetail;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.DateTimeValidation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Create instance for the class
     * @param userRepository create instance for user repository
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserDTO> addUser(UserDTO userDTO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = modelMapper.map(userDTO, User.class);
        String testPasswordEncoded = bCryptPasswordEncoder.encode(user.getPhoneNumber());
        user.setPassword(testPasswordEncoded);
        List<Address> addresses = user.getAddresses();
        addresses.removeAll(user.getAddresses());

        for (AddressDTO addressDTO : userDTO.getAddresses()) {
            addresses.add(modelMapper.map(addressDTO, Address.class));
        }

        user.setCreatedAt(DateTimeValidation.getDate());
        user.setModifiedAt(DateTimeValidation.getDate());

        for (Address address : user.getAddresses()) {
            address.setCreatedAt(DateTimeValidation.getDate());
            address.setModifiedAt(DateTimeValidation.getDate());
        }

        return Optional.of(modelMapper.map(userRepository.save(user), UserDTO.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO getUserById(Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);
        UserDTO fetchedUser = modelMapper.map(user, UserDTO.class);

        if ( user != null && (!user.get().isDeletedStatus()) ) {
            return fetchedUser;
        } else {
            throw new CustomException(Constants.USER_NOT_FOUND);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDTO> getAllUser() {
        return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDTO.class)).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String updateUser(UserDTO userDTO) throws CustomException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = modelMapper.map(userDTO, User.class);
        Optional<User> existUser = userRepository.findById(user.getUserId());
        if(existUser.isEmpty()) {
            throw new CustomException(Constants.USER_NOT_FOUND);
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPhoneNumber()));
        user.setCreatedAt(existUser.get().getCreatedAt());
        user.setModifiedAt(DateTimeValidation.getDate());

        for (Address address : user.getAddresses()) {
            Optional<User> existAddress = userRepository.findById(address.getAddressId());
            if(existAddress.isEmpty()) {
                throw new CustomException(Constants.ADDRESS_NOT_FOUND);
            }
            address.setCreatedAt(existUser.get().getCreatedAt());
            address.setModifiedAt(DateTimeValidation.getDate());
        }

        UserDTO updatedUser = modelMapper.map(userRepository.save(user), UserDTO.class);

        if ( null != updatedUser ) {
            return updatedUser.getName() + Constants.UPDATED_SUCCESSFULLY;
        } else {
            throw new CustomException(Constants.USER_NOT_FOUND);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteUser(Long userId) throws CustomException {
        Optional<User> fetchedUser = userRepository.findById(userId);

        if ( fetchedUser.isPresent() && !fetchedUser.get().isDeletedStatus() ) {
            fetchedUser.get().setDeletedStatus(true);
            Optional<UserDTO> deletedUser = Optional.of(modelMapper.map(userRepository.save(fetchedUser.get()), UserDTO.class));
            return deletedUser.get().getName() + "." + Constants.DELETED_SUCCESSFULLY;
        }
        throw new CustomException(Constants.USER_NOT_FOUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUserExist(Long userId) {
        return userRepository.existsById(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getUserPhoneNumber() {
        return userRepository.findAll().stream().map(User::getPhoneNumber).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getUserEmail() {
        return userRepository.findAll().stream().map(User::getEmailId).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> fetchedUser = Optional.of(userRepository.findByEmailId(username));
        return fetchedUser.map(CustomUserDetail::new).orElse(null);
    }

}
