/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.model;

import com.ideas2it.ideameds.util.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Simple JavaBean domain object representing a User.
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-17
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "is_deleted = false")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotNull
    private String name;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String password;
    @NotNull
    private String emailId;
    @NotNull
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "user_id" ,referencedColumnName = "userId")
    private List<Address> addresses;
    @NotNull
    private boolean isDeleted;
    @OneToMany(mappedBy = "user")
    private List<Prescription> prescriptions;
    @OneToMany(mappedBy = "user")
    private  List<UserMedicine> userMedicines;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role roleType;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime modifiedAt;
}