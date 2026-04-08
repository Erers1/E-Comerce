package gr5.ecomerce.service;

import gr5.ecomerce.dto.OrderDTO;
import gr5.ecomerce.dto.OrderReqDTO;
import gr5.ecomerce.dto.ProfitDTO;
import gr5.ecomerce.entity.Discount;
import gr5.ecomerce.entity.OrderStatus;
import gr5.ecomerce.entity.PaymentMethod;
import gr5.ecomerce.entity.ShippingMethod;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    ResponseEntity<OrderDTO> create(Long userId, OrderReqDTO order);
    ResponseEntity<List<OrderDTO>> getAllOrders();
    ResponseEntity<List<OrderDTO>> getUseOrders(Long userId, int page, int size);
    ResponseEntity<List<OrderDTO>> getSellerOrders(Long sellerId);
    ResponseEntity<OrderDTO> updateStatus(Long id, OrderStatus status);
    ResponseEntity<String> createPayment(Long id);
    void paid(Long id);
    ResponseEntity<OrderDTO> cancel(Long id);
    ResponseEntity<ProfitDTO> getProfitByDay();
    ResponseEntity<ProfitDTO> getProfitByMonth();
}
