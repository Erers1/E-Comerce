package gr5.ecomerce.service;

import gr5.ecomerce.dto.ShippingMethodDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ShippingMethodService {
    ResponseEntity<List<ShippingMethodDTO>> getAll();
    ResponseEntity<String> apply(Long orderId, Long id);
}
