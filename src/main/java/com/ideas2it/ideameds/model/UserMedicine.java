package com.ideas2it.ideameds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

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
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
