package com.ideas2it.ideameds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Simple JavaBean domain object representing Prescription Items
 * @author - Nithish K
 * @version - 1.0
 * @since - 2022-11-17
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class PrescriptionItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionItemsId;

    @NotNull
    private int quantity;

    @NotNull
    private int dosage;

    @NotNull
    private String medicineType;

    @NotNull
    private String medicineName;
}