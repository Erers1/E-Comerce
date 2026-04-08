package gr5.ecomerce.controller;

import gr5.ecomerce.dto.CommentDTO;
import gr5.ecomerce.dto.CommentReqDTO;
import gr5.ecomerce.service.CommentService;
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
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getCommentsByProduct(@RequestParam Long productId) {
        return commentService.getCommentsByProduct(productId);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ROLE_USER')")
    @PostMapping("/write")
    public ResponseEntity<CommentDTO> writeComment(
            @RequestParam Long userId,
            @Valid @RequestBody CommentReqDTO dto) {
        return commentService.writeComment(userId, dto);
    }
}