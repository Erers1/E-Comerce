package gr5.ecomerce.service.Implements;

import gr5.ecomerce.dto.CommentDTO;
import gr5.ecomerce.entity.Comment;
import gr5.ecomerce.entity.Product;
import gr5.ecomerce.entity.User;
import gr5.ecomerce.mapper.CommentMapper;
import gr5.ecomerce.repository.CommentRepository;
import gr5.ecomerce.repository.ProductRepository;
import gr5.ecomerce.repository.UserRepository;
import gr5.ecomerce.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImplement implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public ResponseEntity<List<CommentDTO>> getCommentsByUser(Long id) {
        List<Comment> comments = commentRepository.findAllByUser(id);
        List<CommentDTO> commentDTOList = comments.stream()
                .map(CommentMapper::toDto).toList();
        return ResponseEntity.ok(commentDTOList);
    }

    @Override
    public ResponseEntity<List<CommentDTO>> getCommentsByProduct(Long id) {
        List<Comment> comments = commentRepository.findAllByProduct(id);
        List<CommentDTO> commentDTOList = comments.stream()
                .map(CommentMapper::toDto).toList();
        return ResponseEntity.ok(commentDTOList);
    }

    @Override
    public ResponseEntity<CommentDTO> writeComment(Long userId, Long productId, CommentDTO dto) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(()->new RuntimeException("Product not found"));
        Comment comment = new Comment();
        comment.setRating(dto.getRating());
        comment.setContent(dto.getContent());
        comment.setUser(user);
        comment.setProduct(product);
        commentRepository.save(comment);
        return ResponseEntity.ok(CommentMapper.toDto(comment));
    }

    @Override
    public ResponseEntity<CommentDTO> deleteComment(Long userId, Long productId, Long commentId) {
        Comment comment = commentRepository.findComment(userId, productId, commentId);
        commentRepository.delete(comment);
        return ResponseEntity.ok(CommentMapper.toDto(comment));
    }
}
