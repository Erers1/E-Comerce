package gr5.ecomerce.mapper;

import gr5.ecomerce.dto.CategoriesDTO;
import gr5.ecomerce.dto.ProductDTO;
import gr5.ecomerce.entity.Product;

import java.util.List;

public class ProductMapper {
    public static Product toEntity(ProductDTO dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .img(dto.getImg())
                .sellPrice(dto.getSellPrice())
                .stock(dto.getStock())
                .review(dto.getReview())
                .build();
    }

    public static ProductDTO toDto(Product product) {
        List<CategoriesDTO> categories = product.getCategories().stream()
                .map(CategoryMapper::toDto).toList();
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .img(product.getImg())
                .categories(categories)
                .sellPrice(product.getSellPrice())
                .stock(product.getStock())
                .review(product.getReview())
                .build();
    }
}
