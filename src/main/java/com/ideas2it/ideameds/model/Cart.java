package com.ideas2it.ideameds.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Simple JavaBean domain object representing a cart.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-17
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private Long cartId;
    private double totalPrice;
    private List<CartItem> cartItemList;
}
