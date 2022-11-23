package com.ideas2it.ideameds.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class AddressDTO {
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
