package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.DiscountDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.service.DiscountService;
import com.ideas2it.ideameds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final DiscountService discountService;

    /**
     * Create instance for class
     *
     * @param discountService to create instance for discount service
     */
    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    /**
     * Add discount and save in discount repository.
     *
     * @param discountDTO - To add discount in repository.
     * @return Discount.
     * @throws CustomException - Can not add discount.
     */
    @PostMapping("/discount")
    public ResponseEntity<DiscountDTO> addDiscount(@RequestBody DiscountDTO discountDTO) throws CustomException {
        DiscountDTO savedDiscount = discountService.addDiscount(discountDTO);
        if (savedDiscount != null)
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedDiscount);
        else throw new CustomException(HttpStatus.NO_CONTENT, Constants.CAN_NOT_ADD_DISCOUNT);
    }

    /**
     * Retrieve all discount details from repository.
     *
     * @return All discount details.
     * @throws CustomException - No discount.
     */
    @GetMapping("/allDiscount")
    public ResponseEntity<List<DiscountDTO>> getAllDiscounts() throws CustomException {
        List<DiscountDTO> discountDTOList = discountService.getAll();
        if (discountDTOList != null)
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(discountService.getAll());
        else throw new CustomException(HttpStatus.NOT_FOUND, Constants.NO_DISCOUNT);
    }

    /**
     * To delete discount by discount id.
     *
     * @param discountId - To delete discount by discount id.
     * @return - Response entity.
     * @throws CustomException - Can not delete.
     */
    @DeleteMapping("/discount/{id}")
    public ResponseEntity<String> deleteDiscountById(@PathVariable("id") Long discountId) throws CustomException {
        boolean isDelete = discountService.deleteDiscountById(discountId);
        if (isDelete)
            return ResponseEntity
                    .status(HttpStatus.GONE)
                    .body(Constants.DELETED_SUCCESSFULLY);
        else throw new CustomException(HttpStatus.NOT_FOUND, Constants.CAN_NOT_DELETE);
    }
}