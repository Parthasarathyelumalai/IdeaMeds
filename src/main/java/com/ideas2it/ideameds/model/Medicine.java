package com.ideas2it.ideameds.model;

import com.ideas2it.ideameds.util.IllnessCategories;
import com.ideas2it.ideameds.util.MedicineType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
@Where(clause = "is_deleted_status = false")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicineId;
    @NotNull
    private String medicineName;
    @NotNull
    private String description;
    @Enumerated(EnumType.STRING)
    @NotNull
    private IllnessCategories illnessCategories;
    @Enumerated(EnumType.STRING)
    @NotNull
    private MedicineType medicineType;
    @Column(columnDefinition = "boolean default false")
    @NotNull
    private boolean prescriptionRequired;
    @NotNull
    @Column(columnDefinition = "BIT default 0" )
    private boolean isDeletedStatus;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicine")
    private List<BrandItems> brandItems;
}
