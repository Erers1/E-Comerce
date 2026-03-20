package gr5.ecomerce.dto;

import jakarta.validation.constraints.*;
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
    @NotNull(message = "Hạn mức không được để trống")
    @Min(value = 0, message = "Hạn mức không được âm")
    private BigDecimal limit;
    @NotNull(message = "Giá trị giảm không được để trống")
    @DecimalMin(value = "0.0", message = "Giá trị giảm không được âm")
    @DecimalMax(value = "1.0", message = "Giá trị giảm không được vượt quá 1.0")
    private BigDecimal discountValue;
    private LocalDateTime appliedDate;
    @NotNull(message = "Ngày hết hạn không được để trống")
    @Future(message = "Ngày hết hạn phải ở tương lai")
    private LocalDateTime expiredDate;
}
