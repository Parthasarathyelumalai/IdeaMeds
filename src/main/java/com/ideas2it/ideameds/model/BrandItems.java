package com.ideas2it.ideameds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Simple JavaBean domain object representing Brand Item
 *
 * @author - Dinesh Kumar R
 * @version - 1.0
 * @since - 2022-11-20
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class BrandItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandItemsId;

    private String brandItemName;

    private float price;

    private String sideEffect;

    private String dosage;

    private String manufacturedDate;

    private String expiryDate;

    private String medicineImage;

    private int packageQuantity;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "brand_items_warehouse",
            joinColumns = { @JoinColumn(name = "brand_items_id") },
            inverseJoinColumns = { @JoinColumn(name = "warehouse_id") }
    )
    private List<Warehouse> warehouses;

    @Column(columnDefinition = "BIT default 0" )
    private int deletedStatus;
}
