package gr5.ecomerce.service.Implements;

import gr5.ecomerce.dto.AttributesDTO;
import gr5.ecomerce.dto.CategoriesDTO;
import gr5.ecomerce.dto.ProductDTO;
import gr5.ecomerce.entity.*;
import gr5.ecomerce.mapper.ProductMapper;
import gr5.ecomerce.repository.OrderDetailRepository;
import gr5.ecomerce.repository.ProductRepository;
import gr5.ecomerce.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImplement implements ProductService {
    private final ProductRepository repository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public ResponseEntity<ProductDTO> add(ProductDTO dto) {
        Product product = ProductMapper.toEntity(dto);

        List<Categories> categories = new ArrayList<>();

        for (CategoriesDTO category : dto.getCategories()) {
            Categories cat = Categories.builder()

                    .name(category.getName()).build();
            List<Attributes> attributes = new ArrayList<>();
            for (AttributesDTO attribute : category.getAttributes()) {
                Attributes a = Attributes.builder()
                        .name(attribute.getName())
                        .categories(cat)
                        .build();
                List<ProductAttrValue> productAttrValues = new ArrayList<>();
                for (String value : attribute.getValue()) {
                    ProductAttrValue pav = ProductAttrValue.builder()
                            .attributes(a)
                            .products(product)
                            .value(value).build();
                    productAttrValues.add(pav);
                }
                a.setProductAttrValues(productAttrValues);
                attributes.add(a);
            }
            cat.setAttributes(attributes);
            categories.add(cat);
        }
        product.setCategories(categories);
        repository.save(product);
        return ResponseEntity.ok(ProductMapper.toDto(product));
    }

    @Override
    public ResponseEntity<List<ProductDTO>> addAll(List<ProductDTO> dto) {
        List<ProductDTO> products = new ArrayList<>();
        for (ProductDTO productDTO : dto) {
            products.add(add(productDTO).getBody());
        }
        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").descending());
        List<ProductDTO> products = repository.findAll().stream()
                .map(ProductMapper::toDto).toList();
        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<ProductDTO> delete(Long id) {
        Product product = repository.findById(id)
                        .orElseThrow(()-> new RuntimeException("Product not found"));
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
    public ResponseEntity<ProductDTO> update(Long id, ProductDTO dto) {
        Product product = repository.findById(id)
                .orElseThrow(()->new RuntimeException("Product not found"));
        product.setName(dto.getName());
        product.setSellPrice(dto.getSellPrice());
        product.setStock(dto.getStock());
        product.setImg(dto.getImg());
        repository.save(product);
        return ResponseEntity.ok(ProductMapper.toDto(product));
    }
}
