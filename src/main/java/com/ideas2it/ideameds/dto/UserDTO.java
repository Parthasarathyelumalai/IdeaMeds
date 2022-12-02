/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.dto;

import com.ideas2it.ideameds.model.Prescription;
import com.ideas2it.ideameds.model.UserMedicine;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Represents the User DTO
 *
 * @author Parthasarathy Elumalai
 * @version 1.0
 * @since - 2022-11-19
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    @NotBlank(message = "User name should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_TEXT, message = "Invalid format - e.g. Firstname LastName")
    private String name;
    @NotBlank(message = "Phone number should be mentioned")
    @Pattern(regexp = Constants.REGEX_FOR_PHONE_NUMBER, message = "Invalid format - Start with 6 - 9 e.g. 6**********")
    private String phoneNumber;
    @NotBlank(message = "Email should be mentioned")
    @Email(regexp = Constants.REGEX_FOR_EMAIL_ID, message = "Invalid format e.g. john@xyz.com")
    private String emailId;
    private List<@Valid AddressDTO> addresses;
    private List<Prescription> prescription;
    private List<UserMedicine> userMedicines;
    private Role roleType;
}
