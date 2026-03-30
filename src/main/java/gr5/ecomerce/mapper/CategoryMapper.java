package gr5.ecomerce.mapper;

import gr5.ecomerce.dto.AttributeDTO;
import gr5.ecomerce.dto.CategoryDTO;
import gr5.ecomerce.entity.Category;

import java.util.List;

public class CategoryMapper {
    public static CategoryDTO toDto(Category categories) {
        return CategoryDTO.builder()
                .id(categories.getId())
                .name(categories.getName())
                .build();
    }
}
