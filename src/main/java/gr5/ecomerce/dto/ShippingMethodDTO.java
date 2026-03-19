package gr5.ecomerce.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ShippingMethodDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal fee;
}
