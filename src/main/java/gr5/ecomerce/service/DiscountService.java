package gr5.ecomerce.service;

import gr5.ecomerce.dto.DiscountDTO;
import gr5.ecomerce.entity.Discount;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface DiscountService {
    ResponseEntity<DiscountDTO> create(DiscountDTO dto);
    ResponseEntity<DiscountDTO> delete(Long id);
    ResponseEntity<List<DiscountDTO>> getAll();
    ResponseEntity<String> apply(Long orderId, Long id);
}