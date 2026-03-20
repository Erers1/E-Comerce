package gr5.ecomerce.dto;

import gr5.ecomerce.entity.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderReqDTO {
    private Long id;
    @NotNull(message = "Phương thức thanh toán không được trống")
    private PaymentMethod paymentMethod;
    private List<OrderDetailDTO> item;
}
