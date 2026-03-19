package gr5.ecomerce.service.Implements;

import gr5.ecomerce.dto.UserDTO;
import gr5.ecomerce.entity.User;
import gr5.ecomerce.mapper.UserMapper;
import gr5.ecomerce.repository.UserRepository;
import gr5.ecomerce.security.Utils;
import gr5.ecomerce.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
