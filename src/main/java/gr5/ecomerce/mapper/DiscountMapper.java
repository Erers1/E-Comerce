package gr5.ecomerce.mapper;

import gr5.ecomerce.dto.DiscountDTO;
import gr5.ecomerce.entity.Discount;

public class DiscountMapper {
    public static DiscountDTO toDto(Discount discount) {
        return DiscountDTO.builder()
                .id(discount.getId())
                .description(discount.getDescription())
                .limit(discount.getDiscountLimit())
                .discountValue(discount.getDiscountValue())
                .appliedDate(discount.getAppliedDate())
                .expiredDate(discount.getExpiredDate())
                .build();
    }

    public static Discount toEntity(DiscountDTO dto) {
        return Discount.builder()
                .description(dto.getDescription())
                .discountLimit(dto.getLimit())
                .discountValue(dto.getDiscountValue())
                .appliedDate(dto.getAppliedDate())
                .expiredDate(dto.getExpiredDate())
                .build();
    }
}
