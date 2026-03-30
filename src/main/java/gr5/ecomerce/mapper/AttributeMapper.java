package gr5.ecomerce.mapper;

import gr5.ecomerce.dto.AttributeDTO;
import gr5.ecomerce.entity.Attribute;
import gr5.ecomerce.entity.ProductAttrValue;

import java.util.ArrayList;
import java.util.List;

public class AttributeMapper {
    private static AttributeDTO toDto(Attribute attribute) {
        return AttributeDTO.builder()
                .id(attribute.getId())
                .name(attribute.getName())
                .build();
    };
}
