package kg.attractor.edu_food.service;

import jakarta.transaction.Transactional;
import kg.attractor.edu_food.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getOrdersByUser(String email);

    @Transactional
    void createOrder(String email);
}
