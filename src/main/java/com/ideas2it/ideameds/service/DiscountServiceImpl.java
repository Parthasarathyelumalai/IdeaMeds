/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.DiscountDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.Discount;
import com.ideas2it.ideameds.repository.DiscountRepository;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.DateTimeValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * It contains the implementation of the discount service interface.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-21
 */

@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Create instance for the class
     * @param discountRepository create instance for discount repository
     */
    @Autowired
    public DiscountServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Optional<DiscountDTO> addDiscount(DiscountDTO discountDTO) throws CustomException {

        if (discountDTO != null) {
            Discount discount = modelMapper.map(discountDTO, Discount.class);
            discount.setCreatedAt(DateTimeValidation.getDate());
            discount.setModifiedAt(DateTimeValidation.getDate());
            return Optional.ofNullable(convertToDiscountDto(discountRepository.save(discount)));
        }
        return Optional.empty();
    }

    /**
     * Convert the Discount object to a DiscountDTO object using the modelMapper object.
     *
     * @param discount The object to be converted.
     * @return A DiscountDTO object.
     * @throws CustomException If discount is empty, throws no discount.
     */
    private DiscountDTO convertToDiscountDto(Discount discount) throws CustomException {

        if (null != discount) {
            return modelMapper.map(discount, DiscountDTO.class);
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.NO_DISCOUNT);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<DiscountDTO> getAll() {
        List<Discount> discounts = discountRepository.findAll();
        List<DiscountDTO> discountDTOs = new ArrayList<>();

        for (Discount discount : discounts) {
            discountDTOs.add(modelMapper.map(discount, DiscountDTO.class));
        }
        return discountDTOs;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Optional<DiscountDTO> updateDiscountById(DiscountDTO discountDTO) throws CustomException {

        if (null != discountDTO.getDiscountId()) {
            Optional<Discount> existingDiscount = discountRepository.findById(discountDTO.getDiscountId());

            if (existingDiscount.isPresent() && Objects.equals(existingDiscount.get().getDiscountId(), discountDTO.getDiscountId())) {
                Discount discount = modelMapper.map(discountDTO, Discount.class);
                discount.setCreatedAt(DateTimeValidation.getDate());
                discount.setModifiedAt(DateTimeValidation.getDate());
                return Optional.ofNullable(convertToDiscountDto(discountRepository.save(discount)));
            } else {
                throw new CustomException(HttpStatus.NOT_FOUND, Constants.NO_DISCOUNT);
            }
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.ID_REQUIRED_TO_UPDATE_DISCOUNT);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public boolean deleteDiscountById(Long discountId) throws CustomException {
        Optional<Discount> discount = discountRepository.findById(discountId);

        if (discount.isPresent()) {
            discountRepository.deleteById(discount.get().getDiscountId());
            return true;
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.NO_DISCOUNT);
        }
    }
}
