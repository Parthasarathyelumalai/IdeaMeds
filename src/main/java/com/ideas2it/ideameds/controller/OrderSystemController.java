package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.model.OrderSystem;
import com.ideas2it.ideameds.service.OrderSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for place an order.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-21
 */

@RestController
public class OrderSystemController {
    @Autowired
    OrderSystemService orderSystemService;

    /**
     * Add order items in repository.
     * @param userId - To get user and cart details and map with order.
     * @return - Total price of order.
     */
    @PutMapping("/order/{id}")
    private ResponseEntity<String> addOrder(@PathVariable("id") Long userId) {
        float totalPrice = orderSystemService.addOrder(userId);
        if (totalPrice > 0) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Order successful. Total price is : " + totalPrice);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("order unsuccessful");
        }
    }

    /**
     * All users order details.
     * @return - All users order details.
     */
    @GetMapping("/allorder")
    public ResponseEntity<List<OrderSystem>> getAllCart() {
        return (ResponseEntity.status(HttpStatus.ACCEPTED).body(orderSystemService.getAllOrder()));
    }

    /**
     * To get one order details by user id.
     * @param userId - To get one user order.
     * @return - One user order details
     */
    @GetMapping("/order/{id}")
    public ResponseEntity<OrderSystem> getById(@PathVariable("id") Long userId) {
        OrderSystem orderSystem = orderSystemService.getById(userId);
        if (orderSystem != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(orderSystem);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
