package gr5.ecomerce.service;

import gr5.ecomerce.dto.AuthLoginDTO;
import gr5.ecomerce.dto.OrderDTO;
import gr5.ecomerce.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    ResponseEntity<UserDTO> getUser(HttpServletRequest request);
    ResponseEntity<List<OrderDTO>> getHistory(Long id);
    ResponseEntity<String> setAvarta(MultipartFile file);
    ResponseEntity<List<UserDTO>> getAllUsers();
    void toggleUserStatus(Long id);
}
