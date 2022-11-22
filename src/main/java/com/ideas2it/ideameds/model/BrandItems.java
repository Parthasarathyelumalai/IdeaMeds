package com.ideas2it.ideameds.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Where(clause = "deletedStatus = 0")
public class BrandItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandItemsId;

    private float price;

    private String sideEffect;

    private String dosage;

    private String manufacturedDate;

    private String expiryDate;

    private String medicineImage;

    private int packageQuantity;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(columnDefinition = "BIT default 0" )
    private int deletedStatus;
}
