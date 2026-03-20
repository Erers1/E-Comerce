package gr5.ecomerce.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthLoginDTO {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
