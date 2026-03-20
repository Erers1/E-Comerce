package gr5.ecomerce.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountDTO {
    private Long id;
    private String description;
    private BigDecimal limit;
    private BigDecimal discountValue;
    private LocalDateTime appliedDate;
    private LocalDateTime expiredDate;
    private boolean isExpired;
}
