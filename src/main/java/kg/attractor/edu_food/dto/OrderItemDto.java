package kg.attractor.edu_food.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemDto {
    private String dishName;
    private Integer count;
    private BigDecimal price;
}