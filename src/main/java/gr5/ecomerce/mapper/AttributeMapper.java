package gr5.ecomerce.mapper;

import gr5.ecomerce.dto.AttributesDTO;
import gr5.ecomerce.entity.Attributes;
import gr5.ecomerce.entity.ProductAttrValue;

import java.util.ArrayList;
import java.util.List;

public class AttributeMapper {
    public static AttributesDTO toDto(Attributes attributes) {
        List<ProductAttrValue> productAttrValues = attributes.getProductAttrValues();
        List<String> values = new ArrayList<>();
        for (ProductAttrValue productAttrValue : productAttrValues) {
            values.add(productAttrValue.getValue());
        }
        return AttributesDTO.builder()
                .name(attributes.getName())
                .value(values)
                .build();
    }
}
