package kg.attractor.edu_food.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderDto {
    private Long id;
    private String restaurantName;
    private String date;
    private String status;
    private BigDecimal totalAmount;
    private List<OrderItemDto> items;
}
