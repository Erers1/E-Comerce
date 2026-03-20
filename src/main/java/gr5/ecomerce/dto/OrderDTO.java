package gr5.ecomerce.dto;

import gr5.ecomerce.entity.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private String username;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private Discount discount;
    private OrderStatus orderStatus;
    private ShippingMethod shippingMethod;
    private PaymentMethod paymentMethod;
    private String userNote = "NONE";
}
