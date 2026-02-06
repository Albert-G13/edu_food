package kg.attractor.edu_food.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Long roleId;
}