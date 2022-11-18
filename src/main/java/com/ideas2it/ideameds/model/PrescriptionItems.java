package com.ideas2it.ideameds.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Simple JavaBean domain object representing Prescription Items
 * @author - Nithish K
 * @version - 1.0
 * @since - 2022-11-17
 */
@Data
@NoArgsConstructor
@Entity
public class PrescriptionItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionItemsId;

    private int quantity;
    private int dosage;
    private String medicine_type;

    @ManyToOne
    @JoinColumn(name="prescription_id")
    private Prescription prescription;

    @OneToOne
    @JoinColumn(name="medicine_id")
    private Medicine medicine;
}
