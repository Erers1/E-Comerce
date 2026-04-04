package gr5.ecomerce.service;

import gr5.ecomerce.dto.ProductDTO;
import gr5.ecomerce.dto.TopProductDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<ProductDTO> add(Long seller_id, ProductDTO dto);
    ResponseEntity<List<ProductDTO>> addAll(Long seller_id, List<ProductDTO> dto);
    ResponseEntity<List<ProductDTO>> getAll(int page, int size);
    ResponseEntity<List<ProductDTO>> getBySellerId(Long seller_id, int page, int size);
    ResponseEntity<ProductDTO> delete(Long seller_id, Long id);
    ResponseEntity<ProductDTO> update(Long seller_id, Long id, ProductDTO dto);
    ResponseEntity<List<TopProductDTO>> topProduct();
}
