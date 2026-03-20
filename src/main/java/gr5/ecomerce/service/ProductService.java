package gr5.ecomerce.service;

import gr5.ecomerce.dto.ProductDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<ProductDTO> add(ProductDTO dto);
    ResponseEntity<List<ProductDTO>> addAll(List<ProductDTO> dto);
    ResponseEntity<List<ProductDTO>> getAll(int page, int size);
    ResponseEntity<ProductDTO> delete(Long id);
    ResponseEntity<ProductDTO> update(Long id, ProductDTO dto);
}
