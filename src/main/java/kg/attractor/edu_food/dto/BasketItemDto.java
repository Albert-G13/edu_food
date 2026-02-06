package kg.attractor.edu_food.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasketItemDto {
    private Long dishId;
    private String dishName;
    private String restaurantName;
    private Integer count;
    private BigDecimal price;
    private BigDecimal subTotal;
}
