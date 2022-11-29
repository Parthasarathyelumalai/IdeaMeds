package com.ideas2it.ideameds.dto;

import com.ideas2it.ideameds.util.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class AddressDTO {
    private Long addressId;

    @NotBlank(message = "Plot number should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_PLOT_NUMBER, message = "Invalid format")
    private String plotNumber;

    @NotBlank(message = "Street name should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_TEXT, message = "Invalid format")
    private String streetName;

    @NotBlank(message = "city name should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_TEXT, message = "Invalid format")
    private String cityName;

    @NotBlank(message = "District name should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_TEXT, message = "Invalid format")
    private String districtName;

    @NotBlank(message = "State name should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_TEXT, message = "Invalid format")
    private String stateName;

    @NotBlank(message = "Pin code name should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_PIN_CODE, message = "Invalid format ")
    private String pinCode;
}
