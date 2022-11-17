package com.ideas2it.ideameds.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple JavaBean domain object representing an Address.
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-17
 */
@Data
@NoArgsConstructor
public class Address {
    private Long addressId;
    private String plotNumber;
    private String streetName;
    private String cityName;
    private String districtName;
    private String stateName;
    private String pinCode;
}
