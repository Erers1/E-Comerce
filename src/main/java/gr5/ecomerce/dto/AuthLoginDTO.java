package gr5.ecomerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthLoginDTO {
    @NotNull(message = "Username không được để trống")
    private String username;
    @NotNull(message = "Password không được để trống")
    private String password;
}
