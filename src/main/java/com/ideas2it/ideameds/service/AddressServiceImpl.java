/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.AddressDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.Address;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.repository.AddressRepository;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.DateTimeValidation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation class for Address service
 *
 * @author Parthasarathy Elumalai
 * @version 1.0
 * @since 2022-11-22
 */
@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final ModelMapper modelMapper = new ModelMapper();
    private final AddressRepository addressRepository;

    /**
     * Creates instance for the classes
     *
     * @param addressRepository create object for address repository
     */
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String addAddress(User user, AddressDTO addressDTO) {
        Address address = modelMapper.map(addressDTO, Address.class);
        address.setModifiedAt(DateTimeValidation.getDate());
        address.setCreatedAt(DateTimeValidation.getDate());
        address.setUser(user);
        addressRepository.save(address);
        return user.getName() + Constants.ADDRESS_ADDED_SUCCESSFULLY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteAddress(User user, AddressDTO addressDTO) throws CustomException {
        List<Long> addressesId = user.getAddresses().stream().map(Address::getAddressId).toList();

        for (Long addressId : addressesId) {

            if ( Objects.equals(addressId, addressDTO.getAddressId()) ) {
                Optional<Address> address = addressRepository.findById(addressId);

                if ( address.isPresent() ) {
                    address.get().setModifiedAt(DateTimeValidation.getDate());
                    address.get().setDeleted(true);
                    addressRepository.save(address.get());
                    return user.getName() + Constants.ADDRESS_DELETED_SUCCESSFULLY;
                }
            }
        }
        throw new CustomException(HttpStatus.NOT_FOUND, Constants.ADDRESS_NOT_FOUND);
    }
}
