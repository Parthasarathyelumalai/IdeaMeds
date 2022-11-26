package com.ideas2it.ideameds.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemDto {
    private Long cartItemId;
    private int quantity;
    private MedicineDTO medicineDTO;
    private BrandItemsDTO brandItemsDTO;
}
