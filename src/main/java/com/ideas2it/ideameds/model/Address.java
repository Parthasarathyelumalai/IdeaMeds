/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Simple JavaBean domain object representing an Address.
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-17
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    @NotNull
    private String plotNumber;
    @NotNull
    private String streetName;
    @NotNull
    private String cityName;
    @NotNull
    private String districtName;
    @NotNull
    private String stateName;
    @NotNull
    private String pinCode;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime modifiedAt;
}
