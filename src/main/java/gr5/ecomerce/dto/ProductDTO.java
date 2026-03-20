package gr5.ecomerce.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    @NonNull
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private List<CategoriesDTO> categories;
    @NonNull
    private BigDecimal sellPrice;
    private int stock;
    @NonNull
    private String img;
    @NonNull
    private BigDecimal review;
}
