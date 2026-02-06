package kg.attractor.edu_food.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import kg.attractor.edu_food.dto.BasketItemDto;

import java.util.List;

public interface CartService {
    @Transactional
    void updateQuantity(Long dishId, int delta, String email, String cartCookie, HttpServletResponse response);

    List<BasketItemDto> getBasketItems(String email, String cartCookie);

    void addToCart(Long dishId, String userEmail, String cartCookie, HttpServletResponse response);

    int getBasketCountForUser(String email);

    int getBasketCountFromCookie(String cartCookie);

    @Transactional
    void mergeCart(String email, String cartCookie);
}
