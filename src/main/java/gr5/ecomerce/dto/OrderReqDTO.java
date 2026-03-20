package gr5.ecomerce.dto;

import gr5.ecomerce.entity.PaymentMethod;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderReqDTO {
    private Long id;
    private PaymentMethod paymentMethod;
    private List<OrderDetailDTO> item;
}
