package com.ideas2it.ideameds.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Simple JavaBean domain object representing Medicine.
 *
 * @author - Dinesh Kumar R
 * @version - 1.0
 * @since - 2022-11-17
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicineId;
    private String medicineName;
    private String description;
    private String labelDosage;
    private String medicineUses;
    private String sideEffect;
    private float price;
    private String manufacturedDate;
    private String expiryDate;
    private String medicineImage;
    @Column(columnDefinition = "boolean default false")
    private boolean prescriptionRequired;
    @Column(columnDefinition = "boolean default false")
    private boolean deletedStatus;
    private String createdAt;
    private String modifiedAt;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "medicine_brand",
            joinColumns = { @JoinColumn(name = "medicine_id") },
            inverseJoinColumns = { @JoinColumn(name = "brand_id") }
    )
    private List<Brand> brands;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "medicine_warehouse",
            joinColumns = { @JoinColumn(name = "medicine_id") },
            inverseJoinColumns = { @JoinColumn(name = "warehouse_id") }
    )
    private List<Warehouse> warehouses;
}
