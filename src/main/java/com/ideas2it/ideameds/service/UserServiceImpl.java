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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

/*    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }*/
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     *{@inheritDoc}
     */
    @Override
    public Optional<UserDTO> addUser(UserDTO userDTO) {
/*        String testPasswordEncoded = bCryptPasswordEncoder().encode(userDTO.getPhoneNumber());
        userDTO.setPhoneNumber(testPasswordEncoded);*/
        User user = modelMapper.map(userDTO, User.class);
        List<Address> addresses = user.getAddresses();
        addresses.removeAll(user.getAddresses());
        for(AddressDTO addressDTO : userDTO.getAddresses()) {
            addresses.add(modelMapper.map(addressDTO,Address.class));
        }
        user.setCreatedAt(DateTimeValidation.getDate());
        user.setModifiedAt(DateTimeValidation.getDate());
        for(Address address : user.getAddresses()) {
            address.setCreatedAt(DateTimeValidation.getDate());
            address.setModifiedAt(DateTimeValidation.getDate());
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

        if (fetchedUser != null && (!fetchedUser.isDeletedStatus())) {
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
        user.setModifiedAt(DateTimeValidation.getDate());
        for(Address address : user.getAddresses()) {
            address.setModifiedAt(DateTimeValidation.getDate());
        }
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
        if (userOptional.isPresent() && !userOptional.get().isDeletedStatus() ) {
            user.setDeletedStatus(true);
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

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("inside public UserDetails loadUserByUsername(String username) ");
        User fetchedUser = userRepository.findByEmailId(username);
        log.info(String.valueOf(fetchedUser));
        log.info("inside fetchedUser");
        return new CustomUserDetail(fetchedUser);
    }

}
