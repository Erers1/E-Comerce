package gr5.ecomerce.service;

import gr5.ecomerce.dto.OrderDTO;
import gr5.ecomerce.dto.OrderReqDTO;
import gr5.ecomerce.entity.Discount;
import gr5.ecomerce.entity.PaymentMethod;
import gr5.ecomerce.entity.ShippingMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    ResponseEntity<OrderDTO> create(Long userId, OrderReqDTO order);
    ResponseEntity<OrderDTO> cancel(Long id);
}
