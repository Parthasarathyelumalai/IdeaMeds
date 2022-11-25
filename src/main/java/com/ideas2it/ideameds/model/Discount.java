package com.ideas2it.ideameds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Simple JavaBean domain object representing a cart item.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-17
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;
    private String name;
    private String couponCode;
    private float discount;
    @Column(columnDefinition = "BIT default 0" )
    private int deletedStatus;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
