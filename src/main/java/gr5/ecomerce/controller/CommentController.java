package gr5.ecomerce.controller;

import gr5.ecomerce.dto.CommentDTO;
import gr5.ecomerce.dto.CommentReqDTO;
import gr5.ecomerce.entity.Comment;
import gr5.ecomerce.entity.Product;
import gr5.ecomerce.entity.User;
import gr5.ecomerce.repository.CommentRepository;
import gr5.ecomerce.repository.ProductRepository;
import gr5.ecomerce.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//
@RestController
@RequestMapping("/api/product/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getCommentsByProduct(@RequestParam Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new RuntimeException("product not found"));
        List<Comment> comments = product.getComments();

        List<CommentDTO> dtos = comments.stream().map(c -> CommentDTO.builder()
                .id(c.getId())
                .content(c.getContent())
                .username(c.getUser().getUsername())
                .rating(c.getRating())
                .build()).toList();

        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ROLE_USER')")
    @PostMapping("/write")
    public ResponseEntity<CommentDTO> writeComment(
            @RequestParam Long userId,
            @Valid @RequestBody CommentReqDTO dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .rating(dto.getRating())
                .user(user)
                .product(product)
                .build();

        commentRepository.save(comment);

        CommentDTO responseDto = CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .username(user.getUsername())
                .rating(comment.getRating())
                .build();

        return ResponseEntity.ok(responseDto);
    }
}