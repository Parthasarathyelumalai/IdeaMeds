package com.ideas2it.ideameds.dto;

import com.ideas2it.ideameds.model.Address;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
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
    private String name;
    private String phoneNumber;
    private String emailId;
    private List<Address> addresses;
    private int deletedStatus;
}
