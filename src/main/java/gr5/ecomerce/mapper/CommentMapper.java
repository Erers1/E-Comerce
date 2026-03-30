package gr5.ecomerce.mapper;

import gr5.ecomerce.dto.CommentDTO;
import gr5.ecomerce.entity.Comment;

public class CommentMapper {
    public static CommentDTO toDto(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .username(comment.getUser().getUsername())
                .content(comment.getContent())
                .build();
    }
}
