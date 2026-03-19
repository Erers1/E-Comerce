package gr5.ecomerce.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDTO {
    @NonNull
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private BigDecimal sellPrice;
    private int stock;
    @NonNull
    private String img;
    @NonNull
    private List<String> tag;
    @NonNull
    private BigDecimal review;
}
