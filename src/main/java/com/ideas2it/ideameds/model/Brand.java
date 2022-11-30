package com.ideas2it.ideameds.model;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
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
    private String brandName;
    private String location;
    private String description;
    @Column(columnDefinition = "BIT default 0" )
    @NotNull
    private boolean isDeletedStatus;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brand")
    private List<BrandItems> brandItems;
}
