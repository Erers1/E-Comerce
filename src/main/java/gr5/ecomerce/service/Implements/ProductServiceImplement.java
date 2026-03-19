package gr5.ecomerce.service.Implements;

import gr5.ecomerce.dto.ProductDTO;
import gr5.ecomerce.entity.OrderDetail;
import gr5.ecomerce.entity.Product;
import gr5.ecomerce.mapper.ProductMapper;
import gr5.ecomerce.repository.OrderDetailRepository;
import gr5.ecomerce.repository.ProductRepository;
import gr5.ecomerce.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProductServiceImplement implements ProductService {
    private final ProductRepository repository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public ResponseEntity<ProductDTO> add(ProductDTO dto) {
        Product product = ProductMapper.toEntity(dto);
        repository.save(product);
        return ResponseEntity.ok(ProductMapper.toDto(product));
    }

    @Override
    public ResponseEntity<List<ProductDTO>> addAll(List<ProductDTO> dto) {
        List<ProductDTO> products = new ArrayList<>();
        for (ProductDTO p : dto) {
            Product product = ProductMapper.toEntity(p);
            repository.save(product);
            products.add(ProductMapper.toDto(product));
        }
        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getAll() {
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
