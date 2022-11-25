package com.ideas2it.ideameds.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
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
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;
    private String brandName;
    private String location;
    private String description;
    @Column(columnDefinition = "BIT default 0" )
    private int deletedStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brand")
    private List<BrandItems> brandItems;
    private Date createdAt;
    private Date modifiedAt;
}
