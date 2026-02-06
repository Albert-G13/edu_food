package kg.attractor.edu_food.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "basket")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;
}
