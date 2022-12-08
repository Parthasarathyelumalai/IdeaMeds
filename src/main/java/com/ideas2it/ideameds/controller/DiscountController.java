package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.DiscountDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.service.DiscountService;
import com.ideas2it.ideameds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
     * It adds a discount to the database.
     *
     * @param discountDTO The object that will be sent to the service.
     * @return ResponseEntity<DiscountDTO>
     * @throws CustomException No discount, Can not add discount.
     */
    @PostMapping("/discount")
    public ResponseEntity<DiscountDTO> addDiscount(@RequestBody DiscountDTO discountDTO) throws CustomException {
        Optional<DiscountDTO> savedDiscount = discountService.addDiscount(discountDTO);
        if (savedDiscount.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedDiscount.get());
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.CAN_NOT_ADD_DISCOUNT);
        }
    }

    /**
     * It returns a list of all discounts from the database
     *
     * @return A list of all discounts
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
     * It updates the discount by discountDto object.
     *
     * @param discountDTO The discountDto object to be updated.
     * @return DiscountDto Show the discount dto after update.
     * @throws CustomException  Can not update discount, I'd not found.
     */
    @PutMapping("/discount")
    public ResponseEntity<DiscountDTO> updateDiscountById(@RequestBody DiscountDTO discountDTO) throws CustomException {
        Optional<DiscountDTO> discount = discountService.updateDiscountById(discountDTO);
        if (discount.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(discount.get());
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.CAN_NOT_UPDATE_DISCOUNT);
        }
    }

    /**
     * It deletes a discount by id.
     *
     * @param discountId The id of the discount to be deleted.
     * @return ResponseEntity<String>
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