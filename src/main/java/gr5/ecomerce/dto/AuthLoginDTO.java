package gr5.ecomerce.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthLoginDTO {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
