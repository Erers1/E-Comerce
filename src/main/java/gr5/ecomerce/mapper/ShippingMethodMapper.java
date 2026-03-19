package gr5.ecomerce.mapper;

import gr5.ecomerce.dto.ShippingMethodDTO;
import gr5.ecomerce.entity.ShippingMethod;

public class ShippingMethodMapper {
    public static ShippingMethodDTO toDto(ShippingMethod method) {
        return ShippingMethodDTO.builder()
                .name(method.getName())
                .description(method.getDescription())
                .fee(method.getFee())
                .build();
    }
}
