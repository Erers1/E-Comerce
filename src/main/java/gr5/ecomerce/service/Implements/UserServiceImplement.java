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
}
