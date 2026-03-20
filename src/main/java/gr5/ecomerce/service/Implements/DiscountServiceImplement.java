package gr5.ecomerce.service.Implements;

import gr5.ecomerce.dto.DiscountDTO;
import gr5.ecomerce.entity.Discount;
import gr5.ecomerce.entity.Order;
import gr5.ecomerce.mapper.DiscountMapper;
import gr5.ecomerce.repository.DiscountRepository;
import gr5.ecomerce.repository.OrderRepository;
import gr5.ecomerce.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImplement implements DiscountService {
    private final DiscountRepository discountRepository;
    private final OrderRepository orderRepository;

    @Override
    public ResponseEntity<DiscountDTO> create(DiscountDTO dto) {
        Discount discount = DiscountMapper.toEntity(dto);
        discountRepository.save(discount);
        return ResponseEntity.ok(DiscountMapper.toDto(discount));
    }

    @Override
    public ResponseEntity<DiscountDTO> delete(Long id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No discount info found"));
        discountRepository.delete(discount);
        return ResponseEntity.ok(DiscountMapper.toDto(discount));
    }

    @Override
    public ResponseEntity<List<DiscountDTO>> getAll() {
        List<DiscountDTO> discounts = discountRepository.findAll()
                .stream().map(DiscountMapper::toDto).toList();
        return ResponseEntity.ok(discounts);
    }

    @Override
    public ResponseEntity<String> apply(Long orderId, Long id) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("Order not found"));
        Discount discount = discountRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No discount info found"));

        if (order.getDiscount() != null && order.getDiscount().getId().equals(discount.getId())) {
            throw new RuntimeException("This discount has been applied to your order");
        }

        BigDecimal newTotal = order.getTotalPrice();
        BigDecimal amount = order.getTotalPrice()
                .multiply(discount.getDiscountValue());

        if (discount.getDiscountLimit() != null && amount.compareTo(discount.getDiscountLimit()) > 0) {
            newTotal = newTotal.subtract(discount.getDiscountLimit());
        } else {
            newTotal = newTotal.subtract(amount);
        }

        if (newTotal.compareTo(BigDecimal.ZERO) < 0) {
            newTotal = BigDecimal.ZERO;
        }

        order.setDiscount(discount);
        order.setTotalPrice(newTotal);
        orderRepository.save(order);
        return ResponseEntity.ok("Discount applied successfully on order number: "+ order.getId());
    }
}
