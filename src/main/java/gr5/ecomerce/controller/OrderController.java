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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;
    private final DiscountService discountService;
    private final ShippingMethodService shippingMethodService;

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> create(@RequestParam Long userId,
                                           @Valid @RequestBody OrderReqDTO dto) {
        return service.create(userId, dto);
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<OrderDTO> cancel(@RequestParam Long id) {
        return service.cancel(id);
    }

    @PostMapping("/discount/create")
    public ResponseEntity<DiscountDTO> create(@Valid @RequestBody DiscountDTO dto) {
        return discountService.create(dto);
    }

    @GetMapping("/discount")
    public ResponseEntity<List<DiscountDTO>> getAllDiscount() {
        return discountService.getAll();
    }

    @DeleteMapping("/discount/{id}")
    public ResponseEntity<DiscountDTO> delete(@PathVariable Long id) {
        return discountService.delete(id);
    }

    @PostMapping("/discount")
    public ResponseEntity<String> applyDiscount(@RequestParam Long orderId,@RequestParam Long discountId) {
        return discountService.apply(orderId, discountId);
    }

    @GetMapping("/shipping")
    public ResponseEntity<List<ShippingMethodDTO>> getAllShippingMethods() {
        return shippingMethodService.getAll();
    }

    @PostMapping("/shipping")
    public ResponseEntity<String> applyShippingMethods(@RequestParam Long orderId, @RequestParam Long shippingId) {
        return shippingMethodService.apply(orderId, shippingId);
    }
}
