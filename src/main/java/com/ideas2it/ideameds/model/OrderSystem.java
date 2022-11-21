package com.ideas2it.ideameds.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.stereotype.Component;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

/**
 * Simple JavaBean domain object representing an order.
 *
 * @author - Soundharrajan.S
 * @version - 1.0
 * @since - 2022-11-21
 */

@Entity
@Data
@NoArgsConstructor
@Component
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
}
