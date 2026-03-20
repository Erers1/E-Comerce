package gr5.ecomerce.dto;

import gr5.ecomerce.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    @NotBlank(message = "Username không được có khoảng trắng")
    private String username;
    @NotNull(message = "Password không được trống")
    private String password;
    @NotNull(message = "Email không được trống")
    private String email;
    @NotNull(message = "Số điện thoại không được trống")
    private String phone;
    @NotNull(message = "Địa chỉ không được trống")
    private String address;
    private String avatar;
    private Role role;
}
