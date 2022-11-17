package com.ideas2it.ideameds.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Simple JavaBean domain object representing Brand of a medicine.
 *
 * @author - Dinesh Kumar R
 * @version - 1.0
 * @since - 2022-11-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    private long brandId;
    private String name;
    private String location;
    private String description;
    private List<Medicine> medicines;
}
