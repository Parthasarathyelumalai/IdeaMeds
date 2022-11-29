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
import lombok.RequiredArgsConstructor;
import org.aspectj.asm.IModelFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation for discount.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-21
 */

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     *{@inheritDoc}
     */
    @Override
    public DiscountDTO addDiscount(DiscountDTO discountDTO) {
        if (discountDTO != null) {
            Discount discount = modelMapper.map(discountDTO, Discount.class);
            discount.setCreatedAt(DateTimeValidation.getDate());
            discount.setModifiedAt(DateTimeValidation.getDate());
            return convertToDiscountDto(discountRepository.save(discount));
        }
        return null;
    }

    /**
     * Convert to discount entity to discount dto.
     * @param discount - To convert discount entity  to discount dto.
     * @return - DiscountDto.
     */
    private DiscountDTO convertToDiscountDto(Discount discount) {
        return modelMapper.map(discount, DiscountDTO.class);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<DiscountDTO> getAll() {
        List<Discount> discountList = discountRepository.findAll();
        List<DiscountDTO> discountDTOList = new ArrayList<>();
        for (Discount discount : discountList) {
            discountDTOList.add(modelMapper.map(discount, DiscountDTO.class));
        }
        return discountDTOList;
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
        } else throw new CustomException(Constants.NO_DISCOUNT);
    }
}
