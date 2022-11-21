package com.ideas2it.ideameds.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

/**
 * Simple JavaBean domain object representing Brand of a medicine.
 *
 * @author - Dinesh Kumar R
 * @version - 1.0
 * @since - 2022-11-17
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;
    private String brandName;
    private String location;
    private String description;
<<<<<<< HEAD
    @Column(columnDefinition = "BIT default 0" )
    private int deletedStatus;
=======
    @Column(columnDefinition = "boolean default false")
    private boolean deletedStatus;
>>>>>>> nithish_dev
    @ManyToMany(mappedBy = "brands")
    private List<Medicine> medicines;
}
