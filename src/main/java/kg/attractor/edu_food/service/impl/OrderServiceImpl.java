package kg.attractor.edu_food.service.impl;

import jakarta.transaction.Transactional;
import kg.attractor.edu_food.dto.OrderDto;
import kg.attractor.edu_food.dto.OrderItemDto;
import kg.attractor.edu_food.exception.UserNotFoundException;
import kg.attractor.edu_food.model.*;
import kg.attractor.edu_food.repository.BasketRepository;
import kg.attractor.edu_food.repository.OrderItemRepository;
import kg.attractor.edu_food.repository.OrderRepository;
import kg.attractor.edu_food.repository.UserRepository;
import kg.attractor.edu_food.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;

    @Override
    public List<OrderDto> getOrdersByUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        List<Order> orders = orderRepository.findAllByUserOrderByOrderDateDesc(user);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        return orders.stream().map(order -> {
            BigDecimal total = order.getOrderItems().stream()
                    .map(oi -> oi.getPriceAtPurchase().multiply(BigDecimal.valueOf(oi.getCount())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return OrderDto.builder()
                    .id(order.getId())
                    .restaurantName(order.getRestaurant().getName())
                    .date(order.getOrderDate().format(formatter))
                    .status(order.getStatus())
                    .totalAmount(total)
                    .items(order.getOrderItems().stream()
                            .map(oi -> OrderItemDto.builder()
                                    .dishName(oi.getDish().getName())
                                    .count(oi.getCount())
                                    .price(oi.getPriceAtPurchase())
                                    .build())
                            .toList())
                    .build();
        }).toList();
    }

    @Transactional
    @Override
    public void createOrder(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        List<Basket> items = basketRepository.findAllByUser(user);

        if (items.isEmpty()) return;

        Map<Restaurant, List<Basket>> itemsByRestaurant = items.stream()
                .collect(Collectors.groupingBy(item -> item.getDish().getRestaurant()));

        itemsByRestaurant.forEach((restaurant, basketItems) -> {
            Order order = Order.builder()
                    .user(user)
                    .restaurant(restaurant)
                    .orderDate(LocalDateTime.now())
                    .status("PLACED")
                    .build();

            Order savedOrder = orderRepository.save(order);

            for (Basket item : basketItems) {
                OrderItem oi = OrderItem.builder()
                        .order(savedOrder)
                        .dish(item.getDish())
                        .count(item.getCount())
                        .priceAtPurchase(item.getDish().getPrice())
                        .build();
                orderItemRepository.save(oi);
            }
        });

        basketRepository.deleteAllByUser(user);
    }
}
