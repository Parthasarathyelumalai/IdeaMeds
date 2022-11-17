package com.ideas2it.ideameds.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Simple JavaBean domain object representing a User.
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-17
 */
@Data
@NoArgsConstructor
public class User {
    private Long userId;
    private String name;
    private String phoneNumber;
    private String emailId;
    private List<Address> addresses;
    private List<Medicine> medicines;
    private Prescription prescription;
}
