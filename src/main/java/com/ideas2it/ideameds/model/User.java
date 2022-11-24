package com.ideas2it.ideameds.model;

import com.ideas2it.ideameds.util.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
public class User extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotNull
    private String name;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String emailId;
    @NotNull
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "user_id" ,referencedColumnName = "userId")
    private List<Address> addresses;
    @Column(columnDefinition = "BIT default 0" )
    @NotNull
    private int deletedStatus;
    @OneToMany(mappedBy = "user")
    private List<Prescription> prescription;
    @OneToMany(mappedBy = "user")
    private  List<UserMedicine> userMedicines;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role roleType;
}