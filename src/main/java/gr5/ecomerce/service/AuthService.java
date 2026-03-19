package gr5.ecomerce.service;

import gr5.ecomerce.dto.AuthLoginDTO;
import gr5.ecomerce.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<String> register(UserDTO dto);
    ResponseEntity<String> login(AuthLoginDTO dto);
    ResponseEntity<String> logout(HttpServletRequest request);
}
