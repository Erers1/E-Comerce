package gr5.ecomerce.dto;

import gr5.ecomerce.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopProductDTO {
    private Long id;
    private String name;
    private Long totalSold;
    private BigDecimal sellPrice;
}
