package gr5.ecomerce.controller;

import gr5.ecomerce.dto.CommentDTO;
import gr5.ecomerce.dto.ProductDTO;
import gr5.ecomerce.service.CommentService;
import gr5.ecomerce.service.ImageService;
import gr5.ecomerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;
    private final CommentService commentService;
    private final ImageService imageService;

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping
    public ResponseEntity<ProductDTO> add(@RequestParam Long sellerId, @Valid @RequestBody ProductDTO dto) {
        return service.add(sellerId, dto);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/all")
    public ResponseEntity<List<ProductDTO>> addAll(@RequestParam Long sellerId, @Valid @RequestBody List<ProductDTO> dto) {
        return service.addAll(sellerId, dto);
    }

    @PostMapping("/imgs")
    public ResponseEntity<List<String>> uploadProductImage(@RequestParam Long product_id, @RequestPart("image") List<MultipartFile> files) {
        String filename = "products";
        return imageService.uploadProductImage(product_id, files, filename);
    }

    @PreAuthorize("hasAnyRole('SELLER', 'USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllActiveProduct(@RequestParam int page,@RequestParam int size) {
        return service.getAll(page, size);
    }

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/seller")
    public ResponseEntity<List<ProductDTO>> getAllProductSeller(@RequestParam Long sellerId, @RequestParam int page, @RequestParam int size) {
        return service.getBySellerId(sellerId, page, size);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PutMapping("/update")
    public ResponseEntity<ProductDTO> update(@RequestParam Long sellerId, @RequestParam Long id, @Valid @RequestBody ProductDTO dto) {
        return service.update(sellerId, id, dto);
    }

    @PreAuthorize("hasRole('SELLER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDTO> delete(@RequestParam Long sellerId, @PathVariable Long id) {
        return service.delete(sellerId, id);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/comment")
    public ResponseEntity<List<CommentDTO>> getProductComments(@RequestParam Long productId) {
        return commentService.getCommentsByProduct(productId);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/comment/write")
    public ResponseEntity<CommentDTO> writeComment(@RequestParam Long userId,
                                                   @RequestParam Long productId,
                                                   @Valid @RequestBody CommentDTO commentDTO) {
        return commentService.writeComment(userId, productId, commentDTO);
    }

    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    @DeleteMapping("/comment")
    public ResponseEntity<CommentDTO> deleteComment(@RequestParam Long userId,
                                                    @RequestParam Long productId,
                                                    @RequestParam Long commentId) {
        return commentService.deleteComment(userId, productId, commentId);
    }
}
