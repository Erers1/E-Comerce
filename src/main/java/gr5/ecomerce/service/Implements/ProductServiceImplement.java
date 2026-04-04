package gr5.ecomerce.service.Implements;

import gr5.ecomerce.dto.AttributeDTO;
import gr5.ecomerce.dto.CategoryDTO;
import gr5.ecomerce.dto.ProductDTO;
import gr5.ecomerce.dto.TopProductDTO;
import gr5.ecomerce.entity.*;
import gr5.ecomerce.mapper.ProductMapper;
import gr5.ecomerce.repository.*;
import gr5.ecomerce.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImplement implements ProductService {
    private final ProductRepository repository;
    private final OrderDetailRepository orderDetailRepository;
    private final CategoryRepository categoryRepository;
    private final AttributeRepository attributeRepository;
    private final AttributeValueRepository attributeValueRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ProductDTO> add(Long seller_id, ProductDTO dto) {
        User user = userRepository.findById(seller_id)
                .orElseThrow(()->new RuntimeException("User not found"));
        Product product = ProductMapper.toEntity(dto);
        Set<Category> categories = new HashSet<>();
        List<ProductAttrValue> productAttrValues = new ArrayList<>();
        List<ProductImage> productImages = new ArrayList<>();
        for (CategoryDTO category : dto.getCategories()) {
            Category categoryExist = categoryRepository.findByName(category.getName());
            if (categoryExist == null) {
                categoryExist = Category.builder()
                        .name(category.getName())
                        .build();
                categoryRepository.save(categoryExist);
            }
            categories.add(categoryExist);
        }
        for (AttributeDTO attribute : dto.getAttributes()) {
            Attribute attributeExist = attributeRepository.findByName(attribute.getName());
            if (attributeExist == null) {
                attributeExist = Attribute.builder()
                        .name(attribute.getName())
                        .build();
                attributeRepository.save(attributeExist);
            }
            for (String attributeValue : attribute.getValue()) {
                String val = attributeValue.trim().toLowerCase();
                AttributeValue attributeValueExist = attributeValueRepository
                        .findByValueAndAttribute(attributeExist.getId(), val);
                if (attributeValueExist == null) {
                    attributeValueExist = AttributeValue.builder()
                            .value(val)
                            .attribute(attributeExist)
                            .build();
                    attributeValueRepository.save(attributeValueExist);
                }
                ProductAttrValue productAttrValue = ProductAttrValue.builder()
                        .product(product)
                        .attributeValues(attributeValueExist)
                        .build();
                productAttrValues.add(productAttrValue);
            }
        }
        for (String str : dto.getImg()) {
            ProductImage productImage = ProductImage.builder()
                    .url(str)
                    .product(product)
                    .build();
            productImages.add(productImage);
        }
        product.setSeller(user);
        product.setImg(productImages);
        product.setProductAttrValues(productAttrValues);
        product.setCategories(categories);
        repository.save(product);
        return ResponseEntity.ok(ProductMapper.toDto(product));
    }

    @Override
    public ResponseEntity<List<ProductDTO>> addAll(Long seller_id, List<ProductDTO> dto) {
        List<ProductDTO> products = new ArrayList<>();
        for (ProductDTO productDTO : dto) {
            products.add(add(seller_id, productDTO).getBody());
        }
        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getAll(Long seller_id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").descending());
        List<ProductDTO> products = repository.findBySellerId(seller_id, pageable).stream()
                .map(ProductMapper::toDto).toList();
        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<ProductDTO> delete(Long seller_id, Long id) {
        Product product = repository.findById(id)
                        .orElseThrow(()-> new RuntimeException("Product not found"));
        if (product.getSeller().getId().equals(seller_id)) {
            throw new RuntimeException("Seller not found");
        }
        List<OrderDetail> list = orderDetailRepository.existsByProductId(product.getId());
        if (!list.isEmpty()) {
            throw new RuntimeException("Can't delete product");
        }
        product.setDeleted(true);
        repository.save(product);
        return ResponseEntity.ok(ProductMapper.toDto(product));
    }

    @Override
    @Transactional
    public ResponseEntity<ProductDTO> update(Long seller_id, Long id, ProductDTO dto) {
        Product product = repository.findById(id)
                .orElseThrow(()->new RuntimeException("Product not found"));
        Set<Category> categories = new HashSet<>();
        List<ProductAttrValue> productAttrValues = new ArrayList<>();
        if (product.getSeller().getId().equals(seller_id)) {
            throw new RuntimeException("Seller not found");
        }
        for (CategoryDTO categoryDto : dto.getCategories()) {
            Category categoryExist = categoryRepository.findByName(categoryDto.getName());
            if (categoryExist == null) {
                categoryExist = Category.builder()
                        .name(categoryDto.getName())
                        .build();
                categoryRepository.save(categoryExist);
            }
            categories.add(categoryExist);
        }
        for (AttributeDTO attribute : dto.getAttributes()) {
            Attribute attributeExist = attributeRepository.findByName(attribute.getName());
            if (attributeExist == null) {
                attributeExist = Attribute.builder()
                        .name(attribute.getName())
                        .build();
                attributeRepository.save(attributeExist);
            }
            for (String attributeValue : attribute.getValue()) {
                String val = attributeValue.trim().toLowerCase();
                AttributeValue attributeValueExist = attributeValueRepository
                        .findByValueAndAttribute(attributeExist.getId(), val);
                if (attributeValueExist == null) {
                    attributeValueExist = AttributeValue.builder()
                            .value(val)
                            .attribute(attributeExist)
                            .build();
                    attributeValueRepository.save(attributeValueExist);
                }
                ProductAttrValue productAttrValue = ProductAttrValue.builder()
                        .product(product)
                        .attributeValues(attributeValueExist)
                        .build();
                productAttrValues.add(productAttrValue);
            }
        }
        List<ProductImage> img = new ArrayList<>();
        for (String url : dto.getImg()) {
            ProductImage productImage = ProductImage.builder()
                    .url(url)
                    .product(product)
                    .build();
            img.add(productImage);
        }
        product.getProductAttrValues().clear();
        product.getProductAttrValues().addAll(productAttrValues);
        product.getCategories().clear();
        product.getCategories().addAll(categories);
        product.setName(dto.getName());
        product.setSellPrice(dto.getSellPrice());
        product.setStock(dto.getStock());
        product.setImg(img);
        repository.save(product);
        return ResponseEntity.ok(ProductMapper.toDto(product));
    }

    @Override
    public ResponseEntity<List<TopProductDTO>> topProduct() {
        Pageable pageable = PageRequest.of(0, 10);
        List<TopProductDTO> topProducts = orderDetailRepository.findTopProduct(pageable);
        return ResponseEntity.ok(topProducts);
    }
}
