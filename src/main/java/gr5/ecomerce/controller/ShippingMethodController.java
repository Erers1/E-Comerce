package gr5.ecomerce.controller;

import gr5.ecomerce.dto.ShippingMethodDTO;
import gr5.ecomerce.service.ShippingMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class ShippingMethodController {
    private final ShippingMethodService service;

    @GetMapping("/shipping")
    public ResponseEntity<List<ShippingMethodDTO>> getAll() {
        return service.getAll();
    }

    @PostMapping("/shipping")
    public ResponseEntity<String> apply(@RequestParam Long orderId, @RequestParam Long shippingId) {
        return service.apply(orderId, shippingId);
    }
}
