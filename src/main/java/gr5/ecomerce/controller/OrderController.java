package gr5.ecomerce.controller;

import gr5.ecomerce.dto.DiscountDTO;
import gr5.ecomerce.dto.OrderDTO;
import gr5.ecomerce.dto.OrderReqDTO;
import gr5.ecomerce.dto.ShippingMethodDTO;
import gr5.ecomerce.repository.OrderRepository;
import gr5.ecomerce.service.DiscountService;
import gr5.ecomerce.service.OrderService;
import gr5.ecomerce.service.ShippingMethodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;
    private final DiscountService discountService;
    private final ShippingMethodService shippingMethodService;

    @PreAuthorize("hasAnyAuthority('USER', 'ROLE_USER')")
    @PostMapping("/create")
    public ResponseEntity<OrderDTO> create(@RequestParam Long userId,
                                           @Valid @RequestBody OrderReqDTO dto) {
        return service.create(userId, dto);
    }

    @PreAuthorize("hasAnyAuthority('SELLER', 'ADMIN', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return service.getAllOrders();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'ROLE_USER', 'ROLE_ADMIN')")
    @DeleteMapping("/cancel")
    public ResponseEntity<OrderDTO> cancel(@RequestParam Long id) {
        return service.cancel(id);
    }

    @PreAuthorize("hasAnyAuthority('SELLER', 'ADMIN', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @PostMapping("/discount/create")
    public ResponseEntity<DiscountDTO> create(@Valid @RequestBody DiscountDTO dto) {
        return discountService.create(dto);
    }

    @PostMapping("/momo/create")
    public ResponseEntity<String> createPayment(@RequestParam Long orderId) {
        return service.createPayment(orderId);
    }

    @PostMapping("/momo/callback")
    public ResponseEntity<Void> callback(@RequestBody Map<String, Object> payload) {
        String resultCode = payload.get("resultCode").toString();
        String orderId = payload.get("orderId").toString();
        String[] parts = orderId.split("_");
        Long id = Long.parseLong(parts[0]);

        if ("0".equals(resultCode)) {
            System.out.println("Thanh toán thành công order: " + orderId);
            service.paid(id);
        } else {
            System.out.println("Thanh toán thất bại order: " + orderId);
        }

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'SELLER', 'ROLE_USER', 'ROLE_SELLER')")
    @GetMapping("/discount")
    public ResponseEntity<List<DiscountDTO>> getAllDiscount() {
        return discountService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('SELLER', 'ADMIN', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @DeleteMapping("/discount/{id}")
    public ResponseEntity<DiscountDTO> delete(@PathVariable Long id) {
        return discountService.delete(id);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ROLE_USER')")
    @PostMapping("/discount")
    public ResponseEntity<String> applyDiscount(@RequestParam Long orderId,@RequestParam Long discountId) {
        return discountService.apply(orderId, discountId);
    }

    @PreAuthorize("hasAnyAuthority('SELLER', 'USER', 'ROLE_SELLER', 'ROLE_USER')")
    @GetMapping("/shipping")
    public ResponseEntity<List<ShippingMethodDTO>> getAllShippingMethods() {
        return shippingMethodService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ROLE_USER')")
    @PostMapping("/shipping")
    public ResponseEntity<String> applyShippingMethods(@RequestParam Long orderId, @RequestParam Long shippingId) {
        return shippingMethodService.apply(orderId, shippingId);
    }
}