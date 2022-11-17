package com.ideas2it.ideameds.model;

import lombok.*;

import java.util.List;

/**
 * Simple JavaBean domain object representing Medicine.
 *
 * @author - Dinesh Kumar R
 * @version - 1.0
 * @since - 2022-11-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicine {
    private long medicineId;
    private String name;
    private String description;
    private String labelDosage;
    private String usage;
    private String sideEffect;
    private float price;
    private String manufacturedDate;
    private String expiryDate;
    private String image;
    private Boolean prescriptionRequired;
    private List<Brand> brands;
    private List<Warehouse> warehouses;
}
