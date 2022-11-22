package com.ideas2it.ideameds.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Simple JavaBean domain object representing a User Medicine.
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-17
 */
@Entity
@Data
@NoArgsConstructor
public class UserMedicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userMedicineId;
    @NotNull
    private String medicineName;
    @NotNull
    private int quantity;
    @NotNull
    private String dosage;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
