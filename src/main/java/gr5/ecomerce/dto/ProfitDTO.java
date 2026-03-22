package gr5.ecomerce.dto;

import gr5.ecomerce.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfitDTO {
    private List<OrderDTO> order;
    private BigDecimal total;
}
