package gr5.ecomerce.mapper;

import gr5.ecomerce.dto.AttributesDTO;
import gr5.ecomerce.dto.CategoriesDTO;
import gr5.ecomerce.entity.Categories;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {
    public static CategoriesDTO toDto(Categories categories) {
        List<AttributesDTO> attributes = categories.getAttributes()
                .stream().map(AttributeMapper::toDto).toList();
        return CategoriesDTO.builder()
                .name(categories.getName())
                .attributes(attributes)
                .build();
    }
}
