package com.ideas2it.ideameds.model;

import com.ideas2it.ideameds.util.IllnessCategories;
import com.ideas2it.ideameds.util.MedicineType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Simple JavaBean domain object representing Medicine.
 *
 * @author - Dinesh Kumar R
 * @version - 1.0
 * @since - 2022-11-17
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
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
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicine")
    private List<BrandItems> brandItems;
}
