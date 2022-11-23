package com.ideas2it.ideameds.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ideas2it.ideameds.util.IllnessCategories;
import com.ideas2it.ideameds.util.MedicineType;
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
    @Enumerated(EnumType.STRING)
    private IllnessCategories illnessCategories;
    @Enumerated(EnumType.STRING)
    private MedicineType medicineType;
    @Column(columnDefinition = "boolean default false")
    private boolean prescriptionRequired;
    @Column(columnDefinition = "BIT default 0" )
    private int deletedStatus;
    private String createdAt;
    private String modifiedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicine")
    private List<BrandItems> brandItems;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "medicine_warehouse",
            joinColumns = { @JoinColumn(name = "medicine_id") },
            inverseJoinColumns = { @JoinColumn(name = "warehouse_id") }
    )
    private List<Warehouse> warehouses;
}
