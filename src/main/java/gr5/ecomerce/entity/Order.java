package gr5.ecomerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tblOrder")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "shipping_id")
    private ShippingMethod shippingMethod;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String userNote = "NONE";

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetail = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
