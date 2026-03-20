package gr5.ecomerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDTO {
    @NotNull(message = "Mã đơn hàng không được trống")
    private Long orderId;
    @NotNull(message = "Mã sản phẩm không được trống")
    private Long productId;
    @NotNull(message = "Số lượng mua không được trống")
    private int quantity;
}
