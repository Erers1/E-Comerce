package gr5.ecomerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriesDTO {
    @NotNull(message = "Tên danh mục không được để trống")
    private String name;
    private List<AttributesDTO> attributes;
}
