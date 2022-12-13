/**
 * A CartItemDTO is a data transfer object that represents a cart item
 */
/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * A CartItemDTO is a data transfer object that represents a cart item.
 *
 * @author Soundharrajan S.
 * @version 1.0
 * @since - 2022-11-30
 */
@Getter
@Setter
@NoArgsConstructor

public class CartItemDTO {
    private Long cartItemId;
    @NotBlank(message = "Quantity should be minimum one")
    @Min(1)
    private int quantity;
    private BrandItemDTO brandItemDTO;
}