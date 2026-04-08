package gr5.ecomerce.service.Implements;

import gr5.ecomerce.dto.OrderDTO;
import gr5.ecomerce.dto.ProfitDTO;
import gr5.ecomerce.entity.Order;
import gr5.ecomerce.repository.OrderRepository;
import gr5.ecomerce.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticServiceImplement implements StatisticService {
    private final OrderRepository orderRepository;

    @Override
    public ResponseEntity<ProfitDTO> getMonthlyProfit(Long sellerId) {
        // Tính toán đầu tháng và cuối tháng
        LocalDateTime start = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end = start.plusMonths(1);

        List<Order> orders;
        List<OrderDTO> orderDTOS =  new ArrayList<>();

        if (sellerId != null) {
            orders = orderRepository.getProfitBySeller(start, end, sellerId);
        } else {
            orders = orderRepository.getProfit(start, end);
        }

        BigDecimal total = BigDecimal.ZERO;
        for (Order order : orders) {
            total = total.add(order.getTotalPrice());
            OrderDTO orderDTO = OrderDTO.builder()
                    .id(order.getId())
                    .orderDate(order.getOrderDate())
                    .orderStatus(order.getOrderStatus())
                    .discount(order.getDiscount())
                    .shippingMethod(order.getShippingMethod())
                    .totalPrice(order.getTotalPrice())
                    .userNote(order.getUserNote())
                    .username(order.getUser().getUsername())
                    .paymentMethod(order.getPaymentMethod())
                    .build();
            orderDTOS.add(orderDTO);
        }

        ProfitDTO dto = new ProfitDTO();
        dto.setTotal(total);
        dto.setOrder(orderDTOS);

        return ResponseEntity.ok(dto);
    }
}
