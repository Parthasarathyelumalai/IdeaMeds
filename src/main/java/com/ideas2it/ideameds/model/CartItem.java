package com.ideas2it.ideameds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
public class CartItem extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;
    private int quantity;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_items_id")
    private BrandItems brandItems;
}
