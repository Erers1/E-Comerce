package gr5.ecomerce.mapper;

import gr5.ecomerce.dto.AttributeDTO;
import gr5.ecomerce.dto.CategoryDTO;
import gr5.ecomerce.dto.CommentDTO;
import gr5.ecomerce.dto.ProductDTO;
import gr5.ecomerce.entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductMapper {
    public static Product toEntity(ProductDTO dto) {
        return Product.builder()
                .name(dto.getName())
                .img(new ArrayList<>())
                .sellPrice(dto.getSellPrice())
                .stock(dto.getStock())
                .review(dto.getReview())
                .build();
    }

    public static ProductDTO toDto(Product product) {
        List<CategoryDTO> categories =  new ArrayList<>();
        List<CommentDTO> comments = new ArrayList<>();
        if (product.getComments() != null) {
            comments = product.getComments().stream()
                    .map(CommentMapper::toDto).toList();
        }
        if (product.getCategories() != null) {
            categories = product.getCategories().stream()
                    .map(CategoryMapper::toDto).toList();
        }
        HashMap<String, List<String>> map = new HashMap<>();
        for (ProductAttrValue productAttrValue : product.getProductAttrValues()) {
            String attributeName = productAttrValue.getAttributeValues().getAttribute().getName();
            String value = productAttrValue.getAttributeValues().getValue();
            if (map.containsKey(attributeName)) {
                map.get(attributeName).add(value);
            } else {
                List<String> listVal = new ArrayList<>();
                listVal.add(value);
                map.put(attributeName, listVal);
            }
        }
        List<AttributeDTO> attributes = new ArrayList<>();
        for (String name : map.keySet()) {
            attributes.add(AttributeDTO.builder()
                    .name(name)
                    .value(map.get(name))
                    .build());
        }
        List<String> urls = new ArrayList<>();
        for (ProductImage productImage : product.getImg()) {
            urls.add(productImage.getUrl());
        }
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .img(urls)
                .seller_id(product.getSeller().getId())
                .seller_name(product.getSeller().getUsername())
                .categories(categories)
                .attributes(attributes)
                .sellPrice(product.getSellPrice())
                .stock(product.getStock())
                .review(product.getReview())
                .comments(comments)
                .build();
    }
}
