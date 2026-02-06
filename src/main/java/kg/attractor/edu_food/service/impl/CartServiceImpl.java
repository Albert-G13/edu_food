package kg.attractor.edu_food.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import kg.attractor.edu_food.dto.BasketItemDto;
import kg.attractor.edu_food.exception.DishNotFoundException;
import kg.attractor.edu_food.exception.UserNotFoundException;
import kg.attractor.edu_food.model.Basket;
import kg.attractor.edu_food.model.Dish;
import kg.attractor.edu_food.model.User;
import kg.attractor.edu_food.repository.BasketRepository;
import kg.attractor.edu_food.repository.DishRepository;
import kg.attractor.edu_food.repository.UserRepository;
import kg.attractor.edu_food.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final BasketRepository basketRepository;
    private final DishRepository dishRepository;
    private final UserRepository userRepository;

    private static final String CART_COOKIE_NAME = "cart_items";

    @Transactional
    @Override
    public void updateQuantity(Long dishId, int delta, String email, String cartCookie, HttpServletResponse response) {
        if (email != null) {
            User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
            Optional<Basket> basketOpt = basketRepository.findByUserAndDishId(user, dishId);

            if (basketOpt.isPresent()) {
                Basket basket = basketOpt.get();
                int newCount = basket.getCount() + delta;
                if (newCount <= 0) {
                    basketRepository.delete(basket);
                } else {
                    basket.setCount(newCount);
                    basketRepository.save(basket);
                }
            }
        } else {
            Map<Long, Integer> cart = decodeCart(cartCookie);
            if (cart.containsKey(dishId)) {
                int newCount = cart.get(dishId) + delta;
                if (newCount <= 0) {
                    cart.remove(dishId);
                } else {
                    cart.put(dishId, newCount);
                }
                saveCartToCookie(cart, response);
            }
        }
    }
    @Override
    public List<BasketItemDto> getBasketItems(String email, String cartCookie) {
        if (email != null) {
            return basketRepository.findAllByUserEmail(email).stream()
                    .map(b -> BasketItemDto.builder()
                            .dishId(b.getDish().getId())
                            .dishName(b.getDish().getName())
                            .price(b.getDish().getPrice())
                            .count(b.getCount())
                            .subTotal(b.getDish().getPrice().multiply(BigDecimal.valueOf(b.getCount())))
                            .build())
                    .toList();
        } else {
            Map<Long, Integer> cart = decodeCart(cartCookie);
            return cart.entrySet().stream()
                    .map(e -> {
                        Dish dish = dishRepository.findById(e.getKey()).orElseThrow();
                        return BasketItemDto.builder()
                                .dishId(dish.getId())
                                .dishName(dish.getName())
                                .price(dish.getPrice())
                                .count(e.getValue())
                                .subTotal(dish.getPrice().multiply(BigDecimal.valueOf(e.getValue())))
                                .build();
                    }).toList();
        }
    }

    @Override
    public void addToCart(Long dishId, String userEmail, String cartCookie, HttpServletResponse response) {
        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(UserNotFoundException::new);
            Dish dish = dishRepository.findById(dishId)
                    .orElseThrow(DishNotFoundException::new);

            Optional<Basket> existing = basketRepository.findByUserAndDishId(user, dishId);

            if (existing.isPresent()) {
                Basket basket = existing.get();
                basket.setCount(basket.getCount() + 1);
                basketRepository.save(basket);
            } else {
                Basket newBasket = Basket.builder()
                        .user(user)
                        .dish(dish)
                        .count(1)
                        .build();
                basketRepository.save(newBasket);
            }
        } else {
            Map<Long, Integer> cart = decodeCart(cartCookie);
            cart.put(dishId, cart.getOrDefault(dishId, 0) + 1);
            saveCartToCookie(cart, response);
        }
    }
    @Override
    public int getBasketCountForUser(String email) {
        return basketRepository.findAllByUserEmail(email).stream()
                .mapToInt(Basket::getCount)
                .sum();
    }

    @Override
    public int getBasketCountFromCookie(String cartCookie) {
        if (cartCookie == null || cartCookie.isBlank()) {
            return 0;
        }
        Map<Long, Integer> cart = decodeCart(cartCookie);
        return cart.values().stream().mapToInt(Integer::intValue).sum();
    }

    @Transactional
    @Override
    public void mergeCart(String email, String cartCookie) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        Map<Long, Integer> cookieCart = decodeCart(cartCookie);

        for (Map.Entry<Long, Integer> entry : cookieCart.entrySet()) {
            Long dishId = entry.getKey();
            Integer count = entry.getValue();

            Optional<Basket> existing = basketRepository.findByUserAndDishId(user, dishId);
            if (existing.isPresent()) {
                Basket basket = existing.get();
                basket.setCount(basket.getCount() + count);
                basketRepository.save(basket);
            } else {
                Dish dish = dishRepository.findById(dishId).orElse(null);
                if (dish != null) {
                    basketRepository.save(Basket.builder()
                            .user(user)
                            .dish(dish)
                            .count(count)
                            .build());
                }
            }
        }
    }

    private Map<Long, Integer> decodeCart(String cookieValue) {
        if (cookieValue == null || cookieValue.isEmpty()) return new HashMap<>();
        try {
            String decoded = URLDecoder.decode(cookieValue, StandardCharsets.UTF_8);
            return Arrays.stream(decoded.split("\\|"))
                    .map(s -> s.split(":"))
                    .collect(Collectors.toMap(a -> Long.parseLong(a[0]), a -> Integer.parseInt(a[1])));
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    private void saveCartToCookie(Map<Long, Integer> cart, HttpServletResponse response) {
        String value = cart.entrySet().stream()
                .map(e -> e.getKey() + ":" + e.getValue())
                .collect(Collectors.joining("|"));
        Cookie cookie = new Cookie(CART_COOKIE_NAME, URLEncoder.encode(value, StandardCharsets.UTF_8));
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(cookie);
    }
}
