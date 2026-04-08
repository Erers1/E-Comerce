package gr5.ecomerce.service.Implements;

import gr5.ecomerce.dto.OrderDTO;
import gr5.ecomerce.dto.UserDTO;
import gr5.ecomerce.entity.User;
import gr5.ecomerce.mapper.OrderMapper;
import gr5.ecomerce.mapper.UserMapper;
import gr5.ecomerce.repository.UserRepository;
import gr5.ecomerce.security.Utils;
import gr5.ecomerce.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    private final UserRepository repository;
    private final Utils utils;

    @Override
    public ResponseEntity<UserDTO> getUser(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("AccessToken not found (getUser)!");
        }

        String token = header.substring(7);

        String id = utils.extractId(token);
        User user = repository.findById(Long.parseLong(id))
                .orElseThrow(()-> new RuntimeException("User not found!"));

        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @Override
    public ResponseEntity<List<OrderDTO>> getHistory(Long id) {
        User user = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found!"));
        List<OrderDTO> dto = user.getOrder().stream()
                .map(OrderMapper::toDto).toList();
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<String> setAvarta(MultipartFile file) {
        return null;
    }

    @Override
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = repository.findAll();
        return ResponseEntity.ok(users.stream().map(user -> {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            dto.setRole(user.getRole());
            dto.setIsActive(user.getIsActive());
            return dto;
        }).toList());
    }

    @Override
    public void toggleUserStatus(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User!"));

        // Đảo ngược trạng thái: đang true thành false, đang false thành true
        user.setIsActive(!user.getIsActive());
        repository.save(user);
    }
}
