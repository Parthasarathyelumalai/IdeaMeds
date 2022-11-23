package com.ideas2it.ideameds.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BrandItemsDTO {
    private Long brandItemsId;

    private String brandItemName;

    private float price;

    private String sideEffect;

    private String dosage;

    private String manufacturedDate;

    private String expiryDate;

    private String medicineImage;

    private int packageQuantity;

    private Long medicineId;

    private Long brandId;
}
