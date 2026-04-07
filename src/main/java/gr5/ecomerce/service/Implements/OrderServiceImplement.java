package gr5.ecomerce.service.Implements;

import gr5.ecomerce.config.MomoConfig;
import gr5.ecomerce.dto.*;
import gr5.ecomerce.entity.*;
import gr5.ecomerce.mapper.OrderMapper;
import gr5.ecomerce.repository.OrderRepository;
import gr5.ecomerce.repository.ProductRepository;
import gr5.ecomerce.repository.UserRepository;
import gr5.ecomerce.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Hex;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImplement implements OrderService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final MomoConfig momoConfig;

    @Override
    @Transactional
    public ResponseEntity<OrderDTO> create(Long userId, OrderReqDTO order) {
        Order newOrder = new Order();
        Map<Long, OrderDetail> existed = new HashMap<>();
        Map<Long, Product> productMap = new HashMap<>();

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));

        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setOrderStatus(OrderStatus.PENDING);
        newOrder.setUser(user);

        List<OrderDetail> details = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderDetailDTO dto : order.getItem()) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(()->new RuntimeException("Product not found"));

            if (product.getStock() < dto.getQuantity()) {
                throw new RuntimeException("Stock not enough");
            }

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
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOs = orders.stream()
                .map(OrderMapper::toDto)
                .toList();
        return ResponseEntity.ok(orderDTOs);
    }

    @Override
    public ResponseEntity<List<OrderDTO>> getUseOrders(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Order> orders = orderRepository.findByUserId(userId, pageable);

        List<OrderDTO> orderDTOs = orders.stream()
                .map(OrderMapper::toDto)
                .toList();

        return ResponseEntity.ok(orderDTOs);
    }

    @Override
    public ResponseEntity<OrderDTO> updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderStatus(status);
        orderRepository.save(order);
        return ResponseEntity.ok(OrderMapper.toDto(order));
    }

    @Override
    public ResponseEntity<String> createPayment(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Order not found"));
        String orderId = order.getId() + "_" + System.currentTimeMillis();
        String requestId = UUID.randomUUID().toString();
        String orderInfo = "Thanh toan don hang #" + order.getId();
        String extraData = "";

        String rawSignature = "accessKey=" + momoConfig.getAccessKey()
                + "&amount=" + order.getTotalPrice().longValue()
                + "&extraData=" + extraData
                + "&ipnUrl=" + momoConfig.getIpnUrl()
                + "&orderId=" + orderId
                + "&orderInfo=" + orderInfo
                + "&partnerCode=" + momoConfig.getPartnerCode()
                + "&redirectUrl=" + momoConfig.getRedirectUrl()
                + "&requestId=" + requestId
                + "&requestType=payWithMethod";
        System.out.println(rawSignature);
        String signature = hmacSHA256(rawSignature, momoConfig.getSecretKey());

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("partnerCode", momoConfig.getPartnerCode());
        body.put("accessKey", momoConfig.getAccessKey());
        body.put("requestId", requestId);
        body.put("amount", order.getTotalPrice().longValue());
        body.put("orderId", orderId);
        body.put("orderInfo", orderInfo);
        body.put("redirectUrl", momoConfig.getRedirectUrl());
        body.put("ipnUrl", momoConfig.getIpnUrl());
        body.put("extraData", extraData);
        body.put("requestType", "payWithMethod");
        body.put("signature", signature);
        body.put("lang", "vi");

        ResponseEntity<Map> response = momoConfig.restTemplate().postForEntity(
                momoConfig.getEndpoint(), body, Map.class);
        String payUrl = (String) response.getBody().get("payUrl");
        System.out.println("payUrl: " + payUrl);
        System.out.println("resultCode: " + response.getBody().get("resultCode"));
        System.out.println("message: " + response.getBody().get("message"));
        System.out.println("payUrl: " + response.getBody().get("payUrl"));
        return ResponseEntity.ok(payUrl);
    }

    @Override
    public void paid(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Order not found"));
        order.setOrderStatus(OrderStatus.PAID);
        orderRepository.save(order);
    }

    private String hmacSHA256(String data, String key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKey);
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(hash); // từ commons-codec
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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
