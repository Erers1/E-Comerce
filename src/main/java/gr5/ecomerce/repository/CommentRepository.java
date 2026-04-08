package gr5.ecomerce.repository;

import gr5.ecomerce.dto.CommentDTO;
import gr5.ecomerce.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c where c.user.id = :userId")
    List<Comment> findAllByUser(@Param("userId") Long userId);

    @Query("select c from Comment c where c.product.id = :productId")
    List<Comment> findAllByProduct(@Param("productId") Long productId);

    @Query("""
            select c from Comment c where c.user.id = :userId
            AND c.product.id = :productId
            AND c.id = :commentId
            """)
    Comment findComment(@Param("userId") Long userId,
                        @Param("productId") Long productId,
                        @Param("commentId") Long commentId);
}
