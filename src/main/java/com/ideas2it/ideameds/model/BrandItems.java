package com.ideas2it.ideameds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Simple JavaBean domain object representing Brand Item
 *
 * @author - Dinesh Kumar R
 * @version - 1.0
 * @since - 2022-11-20
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "is_deleted_status = false")
public class BrandItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandItemsId;
    @NotNull
    private String brandItemName;
    @NotNull
    private float price;
    @NotNull
    private String sideEffect;
    @NotNull
    private String dosage;
    @NotNull
    private String manufacturedDate;
    @NotNull
    private String expiryDate;
    @NotNull
    private String medicineImage;
    @NotNull
    private int packageQuantity;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime modifiedAt;

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

    @NotNull
    @Column(columnDefinition = "BIT default 0" )
    private boolean isDeletedStatus;
}