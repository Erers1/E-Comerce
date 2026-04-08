package gr5.ecomerce.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentReqDTO {
    @NotNull
    private Long productId;

    private String content;

    @Min(1) @Max(5)
    private int rating; // Bắt buộc từ 1 đến 5 sao
}