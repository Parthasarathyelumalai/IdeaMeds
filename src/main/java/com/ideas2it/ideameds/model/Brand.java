/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Simple JavaBean domain object representing Brand of a medicine.
 *
 * @author - Dinesh Kumar R
 * @version - 1.0
 * @since - 2022-11-17
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "is_deleted_status = false")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;
    @NotNull
    private String brandName;
    @NotNull
    private String location;
    @NotNull
    private String description;
    @NotNull
    private boolean isDeletedStatus;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime modifiedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brand")
    private List<BrandItems> brandItems;
}
