package gr5.ecomerce.dto;

import gr5.ecomerce.entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    @NotBlank
    private String username;
    @NonNull
    private String password;
    private String email;
    private String phone;
    @NonNull
    private String address;
    private String avatar;
    private Role role;
}
