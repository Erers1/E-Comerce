package gr5.ecomerce.mapper;

import gr5.ecomerce.dto.OrderDTO;
import gr5.ecomerce.entity.Order;
import gr5.ecomerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderMapper {
    public static OrderDTO toDto(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .username(order.getUser().getUsername())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .shippingMethod(order.getShippingMethod())
                .paymentMethod(order.getPaymentMethod())
                .discount(order.getDiscount())
                .userNote(order.getUserNote())
                .build();
    }
}
