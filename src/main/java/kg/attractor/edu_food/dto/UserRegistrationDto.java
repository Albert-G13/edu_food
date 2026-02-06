package kg.attractor.edu_food.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(
            min = 3, max = 20,
            message = "Длина пароля должна быть от 3 до 20 символов"
    )
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).+$",
            message = "Пароль должен содержать хотя бы одну заглавную букву и хотя бы одну цифру"
    )
    private String password;
    private Long roleId;
}
