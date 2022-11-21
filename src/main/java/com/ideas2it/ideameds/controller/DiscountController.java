package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.model.Discount;
import com.ideas2it.ideameds.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller for discount.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-21
 */

@RestController
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    /**
     * Add discount and save in discount repository.
     * @param discount - To add discount in repository.
     * @return Discount.
     */
    @PostMapping("/discount")
    private ResponseEntity<Discount> addDiscount(@RequestBody Discount discount) {
        Discount addedDiscount = discountService.addDiscount(discount);
        if (addedDiscount != null) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(addedDiscount);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Retrieve all discount details from repository.
     * @return All discount details.
     */
    @GetMapping("/alldiscount")
    public ResponseEntity<List<Discount>> getAll() {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(discountService.getAll());
    }
}