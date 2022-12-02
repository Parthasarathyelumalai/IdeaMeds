/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.dto;

import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.util.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

/**
 * Simple JavaBean domain object representing a User Medicine.
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-17
 */
@Getter
@Setter
@NoArgsConstructor
public class UserMedicineDTO {
    private Long userMedicineId;
    @NotBlank(message = "Medicine name should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_TEXT, message = "Invalid format")
    private String medicineName;
    @NotBlank(message = "Quantity should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_NUMBER, message = "Invalid format")
    private int quantity;
}
