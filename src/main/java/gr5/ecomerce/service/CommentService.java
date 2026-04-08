package gr5.ecomerce.service;

import gr5.ecomerce.dto.CommentDTO;
import gr5.ecomerce.dto.CommentReqDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    ResponseEntity<List<CommentDTO>> getCommentsByUser(Long id);
    ResponseEntity<List<CommentDTO>> getCommentsByProduct(Long id);
    ResponseEntity<CommentDTO> writeComment(Long userId, CommentReqDTO dto);
}
