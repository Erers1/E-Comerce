package gr5.ecomerce.dto;

import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashMap;
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
    @NotNull(message = "Sản phẩm phải thuộc ít nhất 1 danh mục")
    private List<CategoryDTO> categories;
    @NotNull(message = "Sản phẩm phải có ít nhất 1 thuộc tính")
    private List<AttributeDTO> attributes;
    @NotNull(message = "Giá bán sản phẩm không được trống")
    private BigDecimal sellPrice;
    @NotNull(message = "Số lượng trong kho không được trống")
    private int stock;
    @NotNull(message = "Mỗi sản phẩm phải có ít nhất 1 ảnh")
    private List<String> img;
    private BigDecimal review;
    private List<CommentDTO> comments;
}
