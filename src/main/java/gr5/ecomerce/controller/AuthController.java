package gr5.ecomerce.controller;

import gr5.ecomerce.dto.AuthLoginDTO;
import gr5.ecomerce.dto.UserDTO;
import gr5.ecomerce.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO dto) {
        return service.register(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthLoginDTO dto) {
        return service.login(dto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        return service.logout(request);
    }

    @GetMapping("/refresh")
    public ResponseEntity<String> refresh(HttpServletRequest request) {
        return service.refresh(request);
    }
}
