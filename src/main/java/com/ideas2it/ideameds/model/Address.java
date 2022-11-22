package com.ideas2it.ideameds.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Simple JavaBean domain object representing an Address.
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-17
 */
@Entity
@Data
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    @NotBlank(message = "Plot number should be mentioned")
    @Pattern(regexp = "^[a-zA-Z0-9/]{1,10}$", message = "Invalid format")
    private String plotNumber;
    @NotBlank(message = "Street name should be mentioned")
    @Pattern(regexp = "^[a-zA-z]{1}[a-zA-Z\\s]*$", message = "Invalid format")
    private String streetName;
    @NotBlank(message = "city name should be mentioned")
    @Pattern(regexp = "^[a-zA-z]{1}[a-zA-Z\\s]*$", message = "Invalid format")
    private String cityName;
    @NotBlank(message = "District name should be mentioned")
    @Pattern(regexp = "^[a-zA-z]{1}[a-zA-Z\\s]*$", message = "Invalid format")
    private String districtName;
    @NotBlank(message = "State name should be mentioned")
    @Pattern(regexp = "^[a-zA-z]{1}[a-zA-Z\\s]*$", message = "Invalid format")
    private String stateName;
    @NotBlank(message = "Pin code name should be mentioned")
    @Pattern(regexp = "^[1-9]{1}[0-9]{5}", message = "Invalid format ")
    private String pinCode;
}
