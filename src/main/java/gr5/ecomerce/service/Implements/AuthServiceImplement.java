package gr5.ecomerce.service.Implements;

import gr5.ecomerce.dto.AuthLoginDTO;
import gr5.ecomerce.dto.UserDTO;
import gr5.ecomerce.entity.Session;
import gr5.ecomerce.entity.User;
import gr5.ecomerce.mapper.UserMapper;
import gr5.ecomerce.repository.SessionRepository;
import gr5.ecomerce.repository.UserRepository;
import gr5.ecomerce.security.Utils;
import gr5.ecomerce.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {
    @Value("${jwt.refreshExpiration}")
    private Long refreshExpiration;
    private final UserRepository repository;
    private final SessionRepository sessionRepository;
    private final Utils utils;

    @Override
    public ResponseEntity<String> register(UserDTO dto) {
        String hashedPassword = utils.encoder().encode(dto.getPassword());
        dto.setPassword(hashedPassword);
        User existedUser = repository.findByName(dto.getUsername());
        if (existedUser != null) {
            throw new RuntimeException("Username or Password has been used!");
        }
        User user = UserMapper.toEntity(dto);
        repository.save(user);
        return ResponseEntity.ok("Đăng ký thành công!");
    }

    @Override
    public ResponseEntity<String> login(AuthLoginDTO dto) {
        User user = repository.findByName(dto.getUsername());
        if(user == null) {
            throw new RuntimeException("Username or Password is wrong!");
        }
        if (!utils.encoder().matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Username or Password is wrong!");
        }

        String accessToken = utils.generateToken(user.getId(), user.getUsername(), user.getRole());
        String refreshToken = utils.generateRefreshToken(user.getId(), user.getUsername());

        Session session = Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .expiredDate(new Date(System.currentTimeMillis()+refreshExpiration))
                .build();

        sessionRepository.save(session);

        ResponseCookie cookie = ResponseCookie.from("refresh-token", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(refreshExpiration/1000)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(accessToken);
    }

    @Override
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new RuntimeException("Cookies is null");
        }
        for (Cookie cookie : cookies) {
            if ("refresh-token".equals(cookie.getName())) {
                refreshToken = cookie.getValue();
            }
        }

        if (refreshToken == null) {
            throw new RuntimeException("You have to login!");
        }

        sessionRepository.delByToken(refreshToken);

        ResponseCookie deletedCookie = ResponseCookie.from("refresh-token", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, deletedCookie.toString())
                .body("Đăng xuất thành công!");
    }
}
