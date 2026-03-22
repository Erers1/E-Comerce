package gr5.ecomerce.service.Implements;

import gr5.ecomerce.dto.*;
import gr5.ecomerce.entity.*;
import gr5.ecomerce.mapper.OrderMapper;
import gr5.ecomerce.repository.OrderRepository;
import gr5.ecomerce.repository.ProductRepository;
import gr5.ecomerce.repository.UserRepository;
import gr5.ecomerce.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImplement implements OrderService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public ResponseEntity<OrderDTO> create(Long userId, OrderReqDTO order) {
        Order newOrder = new Order();
        Map<Long, OrderDetail> existed = new HashMap<>();
        Map<Long, Product> productMap = new HashMap<>();

        // Kiểm tra thông tin khách hàng
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));

        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setOrderStatus(OrderStatus.PENDING);
        newOrder.setUser(user);

        List<OrderDetail> details = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        //Kiểm tra số lượng mua
        for (OrderDetailDTO dto : order.getItem()) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(()->new RuntimeException("Product not found"));

            if (product.getStock() < dto.getQuantity()) {
                throw new RuntimeException("Stock not enough");
            }

            //Kiểm tra trùng lặp hàng hóa
            if (existed.containsKey(product.getId())) {
                OrderDetail existedDetail = existed.get(product.getId());
                int newQuan = existedDetail.getQuantity() + dto.getQuantity();
                existedDetail.setQuantity(newQuan);
            } else {
                OrderDetail detail = OrderDetail.builder()
                        .order(newOrder)
                        .product(product)
                        .quantity(dto.getQuantity())
                        .build();
                existed.put(product.getId(), detail);
                productMap.put(product.getId(), product);
            }
        }

        //Tính tiền từng sản phẩm
        for (OrderDetail detail : existed.values()) {
            BigDecimal subTotal = productMap.get(detail.getProduct().getId())
                    .getSellPrice().multiply(BigDecimal.valueOf(detail.getQuantity()));
            total = total.add(subTotal);
            detail.setSubTotal(subTotal);
            details.add(detail);
        }

        newOrder.setTotalPrice(total);
        newOrder.setOrderDetail(details);
        newOrder.setPaymentMethod(order.getPaymentMethod());
        orderRepository.save(newOrder);

        List<Order> orders = user.getOrder();
        orders.add(newOrder);
        user.setOrder(orders);
        userRepository.save(user);

        return ResponseEntity.ok(OrderMapper.toDto(newOrder));
    }

    @Override
    public ResponseEntity<OrderDTO> cancel(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Order not found"));
        if (order.getOrderStatus() != OrderStatus.DELIVERIED &&
            order.getOrderStatus() != OrderStatus.COMPLETED) {
            order.setOrderStatus(OrderStatus.CANCELED);
        } else {
            throw new RuntimeException("Your order has been deliveried or on their way!");
        }
        return ResponseEntity.ok(OrderMapper.toDto(order));
    }

    @Override
    public ResponseEntity<ProfitDTO> getProfitByDay() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();
        List<Order> orders = orderRepository.getProfit(start, end);
        BigDecimal total = BigDecimal.ZERO;
        List<ProfitDTO> profits = new ArrayList<>();
        for (Order order : orders) {
            total = total.add(order.getTotalPrice());
        }
        List<OrderDTO> orderDTOS = orders.stream()
                .map(OrderMapper::toDto).toList();
        ProfitDTO profitDTO = ProfitDTO.builder()
                .order(orderDTOS)
                .total(total)
                .build();
        return ResponseEntity.ok(profitDTO);
    }

    @Override
    public ResponseEntity<ProfitDTO> getProfitByMonth() {
        LocalDateTime startOfMonth = LocalDate.now()
                .withDayOfMonth(1)
                .atStartOfDay();
        LocalDateTime endOfMonth = LocalDate.now()
                .plusMonths(1)
                .withDayOfMonth(1)
                .atStartOfDay();
        List<Order> orders = orderRepository.getProfit(startOfMonth, endOfMonth);
        BigDecimal total = BigDecimal.ZERO;
        List<ProfitDTO> profits = new ArrayList<>();
        for (Order order : orders) {
            total = total.add(order.getTotalPrice());
        }
        List<OrderDTO> orderDTOS = orders.stream()
                .map(OrderMapper::toDto).toList();
        ProfitDTO profitDTO = ProfitDTO.builder()
                .order(orderDTOS)
                .total(total)
                .build();
        return ResponseEntity.ok(profitDTO);
    }
}
