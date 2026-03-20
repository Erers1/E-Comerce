package gr5.ecomerce.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDTO {
    private Long orderId;
    private Long productId;
    private int quantity;
}
