package kg.attractor.edu_food.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
