package com.ideas2it.ideameds.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple JavaBean domain object representing a cart item.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
    private Long discountId;
    private String name;
    private String couponCode;
    private float discount;
}
