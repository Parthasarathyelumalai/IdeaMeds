/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.AddressDTO;
import com.ideas2it.ideameds.dto.ResponseUserDTO;
import com.ideas2it.ideameds.dto.UserDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.Address;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.repository.AddressRepository;
import com.ideas2it.ideameds.repository.UserRepository;
import com.ideas2it.ideameds.security.CustomUserDetail;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.DateTimeValidation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private final AddressRepository addressRepository;
    private final AddressService addressService;

    /**
     * Create instance for the class
     *
     * @param userRepository create instance for user repository
     * @param addressService create instance for userAddress repository
     * @param addressRepository create instance for address repository
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, AddressService addressService, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.addressRepository = addressRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserDTO> addUser(UserDTO userDTO) throws CustomException {
        if ( validUserByEmailId(userDTO.getEmailId()) && validUserByPhoneNumber(userDTO.getPhoneNumber()) ) {
            throw new CustomException(HttpStatus.NOT_ACCEPTABLE, Constants.EMAIL_ID_PHONE_NUMBER_EXISTS);
        } else if ( validUserByEmailId(userDTO.getEmailId()) ) {
            throw new CustomException(HttpStatus.NOT_ACCEPTABLE, Constants.EMAIL_ID_EXISTS);
        } else if ( validUserByPhoneNumber(userDTO.getPhoneNumber()) ) {
            throw new CustomException(HttpStatus.NOT_ACCEPTABLE, Constants.PHONE_NUMBER_EXISTS);
        } else {
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

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseUserDTO getUserDTOById(Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);

        if ( user.isPresent() ) {
            return modelMapper.map(user, ResponseUserDTO.class);
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserById(Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);

        if ( user.isPresent() ) {
            return user.get();
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ResponseUserDTO> getAllUser() {
        return userRepository.findAll().stream().map(user -> modelMapper.map(user, ResponseUserDTO.class)).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String updateUser(UserDTO userDTO) throws CustomException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = modelMapper.map(userDTO, User.class);
        Optional<User> existingUser = userRepository.findById(user.getUserId());
        if ( existingUser.isPresent() ) {
            validByPhoneNumber(user);
            validByEmailId(user);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPhoneNumber()));
            user.setCreatedAt(existingUser.get().getCreatedAt());
            user.setModifiedAt(DateTimeValidation.getDate());

            for (Address address : user.getAddresses()) {
                Optional<Address> existingAddress = addressRepository.findById(address.getAddressId());
                if (existingAddress.isEmpty()) {
                    throw new CustomException(HttpStatus.NOT_FOUND, Constants.ADDRESS_NOT_FOUND);
                }
                address.setCreatedAt(existingUser.get().getCreatedAt());
                address.setModifiedAt(DateTimeValidation.getDate());
            }

            UserDTO updatedUser = modelMapper.map(userRepository.save(user), UserDTO.class);

            if ( null != updatedUser ) {
                return updatedUser.getName() + Constants.UPDATED_SUCCESSFULLY;
            } else {
                throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
            }
        }
        throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteUser(Long userId) throws CustomException {
        Optional<User> existingUser = userRepository.findById(userId);

        if ( existingUser.isPresent() && !existingUser.get().isDeleted() ) {
            existingUser.get().setDeleted(true);
            Optional<UserDTO> deletedUser = Optional.of(modelMapper
                    .map(userRepository.save(existingUser.get()), UserDTO.class));
            return deletedUser.get().getName() + "." + Constants.DELETED_SUCCESSFULLY;
        }
        throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
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
    public UserDetails loadUserByUsername(String username) {
        Optional<User> existingUser = Optional.of(userRepository.findByEmailId(username));
        return existingUser.map(CustomUserDetail::new).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String addUserAddress(Long userId, AddressDTO addressDTO) throws CustomException {
        User user = modelMapper.map(getUserDTOById(userId), User.class);
        return addressService.addAddress(user, addressDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteUserAddress(Long userId, AddressDTO addressDTO) throws CustomException {
        User user = modelMapper.map(getUserDTOById(userId), User.class);
        return addressService.deleteAddress(user, addressDTO);
    }

    /**
     * To valid User By phone number
     *
     * @param userPhoneNumber - send a user Email id to validate
     * @return boolean - true or false
     */
    private boolean validUserByPhoneNumber(String userPhoneNumber) {
        List<String> userPhoneNumbers = userRepository.findAll().stream().map(User::getPhoneNumber).toList();
        for (String number : userPhoneNumbers) {
            if ( number.equals(userPhoneNumber) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * To valid User By EmailId
     *
     * @param userEmailId - send a user Email id to validate
     * @return boolean - true or false
     */
    private boolean validUserByEmailId(String userEmailId) {
        List<String> userEmailIds = userRepository.findAll().stream().map(User::getEmailId).toList();
        for (String emailId : userEmailIds) {
            if ( userEmailId.equals(emailId) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * valid email id before update
     *
     * @param user - pass user details
     * @throws CustomException - occur when email id already exit
     */
    private void validByEmailId(User user) throws CustomException {
        if ( validUserByEmailId(user.getEmailId()) ) {
            User existingUser = userRepository.findByEmailId(user.getEmailId());
            if ( !user.getUserId().equals(existingUser.getUserId()) )
                throw new CustomException(HttpStatus.NOT_ACCEPTABLE, Constants.EMAIL_ID_EXISTS);
        }
    }

    /**
     * valid phone number before update
     *
     * @param user - pass user details
     * @throws CustomException - occur when email id already exit
     */
    private void validByPhoneNumber(User user) throws CustomException {
        if ( validUserByPhoneNumber(user.getPhoneNumber()) ) {
            User existingUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
            if ( !user.getUserId().equals(existingUser.getUserId()) )
                throw new CustomException(HttpStatus.NOT_ACCEPTABLE, Constants.PHONE_NUMBER_EXISTS);
        }
    }
}
