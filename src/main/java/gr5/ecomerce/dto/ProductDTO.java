package gr5.ecomerce.dto;

import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    @NotNull(message = "Tên sản phẩm không được trống")
    private String name;
    private List<CategoriesDTO> categories;
    @NotNull(message = "Giá bán sản phẩm không được trống")
    private BigDecimal sellPrice;
    @NotNull(message = "Số lượng trong kho không được trống")
    private int stock;
    private String img;
    private BigDecimal review;
}
