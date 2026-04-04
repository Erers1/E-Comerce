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

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<OrderDTO> create(@RequestParam Long userId,
                                           @Valid @RequestBody OrderReqDTO dto) {
        return service.create(userId, dto);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/cancel")
    public ResponseEntity<OrderDTO> cancel(@RequestParam Long id) {
        return service.cancel(id);
    }

    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
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

    @PreAuthorize("hasAnyRole('USER', 'SELLER')")
    @GetMapping("/discount")
    public ResponseEntity<List<DiscountDTO>> getAllDiscount() {
        return discountService.getAll();
    }

    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    @DeleteMapping("/discount/{id}")
    public ResponseEntity<DiscountDTO> delete(@PathVariable Long id) {
        return discountService.delete(id);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/discount")
    public ResponseEntity<String> applyDiscount(@RequestParam Long orderId,@RequestParam Long discountId) {
        return discountService.apply(orderId, discountId);
    }
    @PreAuthorize("hasAnyRole('SELLER', 'USER')")
    @GetMapping("/shipping")
    public ResponseEntity<List<ShippingMethodDTO>> getAllShippingMethods() {
        return shippingMethodService.getAll();
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/shipping")
    public ResponseEntity<String> applyShippingMethods(@RequestParam Long orderId, @RequestParam Long shippingId) {
        return shippingMethodService.apply(orderId, shippingId);
    }
}
