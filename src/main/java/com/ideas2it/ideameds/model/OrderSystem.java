package com.ideas2it.ideameds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Simple JavaBean domain object representing an order.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-21
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String orderDate;
    private String deliveryDate;
    private String orderStatus;
    private float totalPrice;
    private float discountPrice;
    private float discountPercentage;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "discount_id")
    private Discount discount;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItemList;
    @Column(columnDefinition = "BIT default 0" )
    private int deletedStatus;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
