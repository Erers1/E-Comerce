package gr5.ecomerce.controller;

import gr5.ecomerce.dto.CommentDTO;
import gr5.ecomerce.dto.ProductDTO;
import gr5.ecomerce.dto.TopProductDTO;
import gr5.ecomerce.service.CommentService;
import gr5.ecomerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;
    private final CommentService commentService;

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping
    public ResponseEntity<ProductDTO> add(@Valid @RequestBody ProductDTO dto) {
        return service.add(dto);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/all")
    public ResponseEntity<List<ProductDTO>> addAll(@Valid @RequestBody List<ProductDTO> dto) {
        return service.addAll(dto);
    }

    @PreAuthorize("hasRole('SELLER, USER')")
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll(@RequestParam int page,@RequestParam int size) {
        return service.getAll(page, size);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PutMapping("/update")
    public ResponseEntity<ProductDTO> update(@RequestParam Long id, @Valid @RequestBody ProductDTO dto) {
        return service.update(id, dto);
    }

    @PreAuthorize("hasRole('SELLER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDTO> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @PreAuthorize("hasRole('USER, ADMIN')")
    @GetMapping("/comment")
    public ResponseEntity<List<CommentDTO>> getProductComments(@RequestParam Long productId) {
        return commentService.getCommentsByProduct(productId);
    }

    @PreAuthorize("hasRole('USER, ADMIN')")
    @PostMapping("/comment/write")
    public ResponseEntity<CommentDTO> writeComment(@RequestParam Long userId,
                                                   @RequestParam Long productId,
                                                   @Valid @RequestBody CommentDTO commentDTO) {
        return commentService.writeComment(userId, productId, commentDTO);
    }

    @PreAuthorize("hasRole('SELLER, ADMIN')")
    @DeleteMapping("/comment")
    public ResponseEntity<CommentDTO> deleteComment(@RequestParam Long userId,
                                                    @RequestParam Long productId,
                                                    @RequestParam Long commentId) {
        return commentService.deleteComment(userId, productId, commentId);
    }
}
