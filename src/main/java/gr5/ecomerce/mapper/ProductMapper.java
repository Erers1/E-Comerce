package gr5.ecomerce.mapper;

import gr5.ecomerce.dto.ProductDTO;
import gr5.ecomerce.entity.Product;

public class ProductMapper {
    public static Product toEntity(ProductDTO dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .img(dto.getImg())
                .tag(dto.getTag())
                .sellPrice(dto.getSellPrice())
                .stock(dto.getStock())
                .review(dto.getReview())
                .build();
    }

    public static ProductDTO toDto(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .img(product.getImg())
                .tag(product.getTag())
                .sellPrice(product.getSellPrice())
                .stock(product.getStock())
                .review(product.getReview())
                .build();
    }
}
