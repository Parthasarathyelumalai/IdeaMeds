package com.ideas2it.ideameds.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiscountDTO {
    private Long discountId;
    private String name;
    private String couponCode;
    private float discount;
    private int deletedStatus;
}
