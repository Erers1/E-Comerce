package gr5.ecomerce.controller;

import gr5.ecomerce.dto.OrderDTO;
import gr5.ecomerce.dto.OrderReqDTO;
import gr5.ecomerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> create(@RequestParam Long userId,
                                           @Valid @RequestBody OrderReqDTO dto) {
        return service.create(userId, dto);
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<OrderDTO> cancel(@RequestParam Long id) {
        return service.cancel(id);
    }
}
