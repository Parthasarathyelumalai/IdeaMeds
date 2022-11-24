/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Discount;
import com.ideas2it.ideameds.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for discount.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-21
 */

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    /**
     *{@inheritDoc}
     */
    @Override
    public Discount addDiscount(Discount discount) {
        if (discount != null) {
            return discountRepository.save(discount);
        }
        return null;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<Discount> getAll() {
        return discountRepository.findAll();
    }


}
