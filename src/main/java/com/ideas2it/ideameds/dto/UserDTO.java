package com.ideas2it.ideameds.dto;

import com.ideas2it.ideameds.model.Address;
import com.ideas2it.ideameds.model.Prescription;
import com.ideas2it.ideameds.model.UserMedicine;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Represents the User DTO
 * @author Parthasarathy Elumalai
 * @version 1.0
 * @since - 2022-11-19
 */
@Data
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    @NotBlank(message = "User name should be mentioned")
    @Pattern(regexp = "^[a-zA-z]{1}[a-zA-Z\\s]*$", message = "Invalid format - e.g. Firstname LastName")
    private String name;
    @NotBlank(message = "Phone number should be mentioned")
    @Pattern(regexp = "^[6-9]{1}[0-9]{9}", message = "Invalid format - Start with 6 - 9 e.g. 6**********")
    private String phoneNumber;
    @NotBlank(message = "Email should be mentioned")
    @Email(regexp = "^[a-z]{1}[a-z0-9._]+@[a-z0-9]+[.][a-z]*$",message = "Invalid format e.g. john@xyz.com")
    private String emailId;
    private List<Address> addresses;
    private List<Prescription> prescription;
    private  List<UserMedicine> userMedicines;
}
