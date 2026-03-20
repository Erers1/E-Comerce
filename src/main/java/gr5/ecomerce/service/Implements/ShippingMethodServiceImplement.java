package gr5.ecomerce.service.Implements;

import gr5.ecomerce.dto.ShippingMethodDTO;
import gr5.ecomerce.entity.Order;
import gr5.ecomerce.entity.ShippingMethod;
import gr5.ecomerce.mapper.ShippingMethodMapper;
import gr5.ecomerce.repository.OrderRepository;
import gr5.ecomerce.repository.ShippingMethodRepository;
import gr5.ecomerce.service.ShippingMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingMethodServiceImplement implements ShippingMethodService {
    private final ShippingMethodRepository shippingMethodRepository;
    private final OrderRepository orderRepository;

    @Override
    public ResponseEntity<List<ShippingMethodDTO>> getAll() {
        List<ShippingMethodDTO> methods = shippingMethodRepository.findAll()
                .stream().map(ShippingMethodMapper::toDto).toList();
        return ResponseEntity.ok(methods);
    }

    @Override
    public ResponseEntity<String> apply(Long orderId, Long id) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("Order not found"));
        ShippingMethod shippingMethod = shippingMethodRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Shipping method not found"));

        BigDecimal newTotal = order.getTotalPrice().add(shippingMethod.getFee());
        order.setTotalPrice(newTotal);
        orderRepository.save(order);
        return ResponseEntity.ok("Shipping method applied successfully on order number: "+ order.getId());
    }
}
