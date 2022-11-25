package com.ideas2it.ideameds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;

/**
 * Simple JavaBean domain object representing Warehouse details.
 *
 * @author - Dinesh Kumar R
 * @version - 1.0
 * @since - 2022-11-17
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseId;
    private String warehouseName;
    private String location;

    @Column(columnDefinition = "BIT default 0" )
    private int deletedStatus;

    @ManyToMany(mappedBy = "warehouses")
    private List<BrandItems> brandItemsList;
    private Date createdAt;
    private Date modifiedAt;
}
