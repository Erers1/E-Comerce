package gr5.ecomerce.controller;

import gr5.ecomerce.dto.CommentDTO;
import gr5.ecomerce.dto.UserDTO;
import gr5.ecomerce.service.CommentService;
import gr5.ecomerce.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<UserDTO> getUser(HttpServletRequest request) {
        return service.getUser(request);
    }

    @GetMapping("/comment")
    public ResponseEntity<List<CommentDTO>> getComments(@RequestParam Long userId) {
        return commentService.getCommentsByUser(userId);
    }
}
