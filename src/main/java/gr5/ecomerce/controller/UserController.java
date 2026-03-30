package gr5.ecomerce.controller;

import gr5.ecomerce.dto.CommentDTO;
import gr5.ecomerce.dto.OrderDTO;
import gr5.ecomerce.dto.UserDTO;
import gr5.ecomerce.service.CommentService;
import gr5.ecomerce.service.ImageService;
import gr5.ecomerce.service.OrderService;
import gr5.ecomerce.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final CommentService commentService;
    private final ImageService imageService;

    @PostMapping("/imgs")
    public ResponseEntity<String> setAvarta(@RequestParam Long user_id, @RequestPart("image") MultipartFile files) {
        String filename = "users";
        return imageService.setAvarta(user_id, files, filename);
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUser(HttpServletRequest request) {
        return service.getUser(request);
    }

    @GetMapping("/comment")
    public ResponseEntity<List<CommentDTO>> getComments(@RequestParam Long userId) {
        return commentService.getCommentsByUser(userId);
    }

    @GetMapping("/history")
    public ResponseEntity<List<OrderDTO>> getHistory(@RequestParam Long userId) {
        return service.getHistory(userId);
    }
}
