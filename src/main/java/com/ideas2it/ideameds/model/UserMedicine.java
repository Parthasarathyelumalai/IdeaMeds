package com.ideas2it.ideameds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Simple JavaBean domain object representing a User Medicine.
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-17
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserMedicine extends Base{
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
