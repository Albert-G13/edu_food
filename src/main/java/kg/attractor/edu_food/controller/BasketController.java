package kg.attractor.edu_food.controller;

import jakarta.servlet.http.HttpServletResponse;
import kg.attractor.edu_food.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {
    private final CartService cartService;

    @PostMapping("/add")
    public String addToCart(@RequestParam Long dishId,
                            Authentication auth,
                            @CookieValue(value = "cart_items", defaultValue = "") String cartCookie,
                            HttpServletResponse response) {
        String email = (auth != null) ? auth.getName() : null;
        log.info("Adding dish ID: {} to cart for user: {}", dishId, email);
        cartService.addToCart(dishId, email, cartCookie, response);
        return "redirect:/restaurants";
    }
    @GetMapping
    public String showBasket(Authentication auth,
                             @CookieValue(value = "cart_items", defaultValue = "") String cartCookie,
                             Model model) {
        String email = (auth != null) ? auth.getName() : null;
        log.info("User {} is viewing their basket", email);
        var items = cartService.getBasketItems(email, cartCookie);
        model.addAttribute("basketItems", items);
        return "basket/basket";
    }
    @PostMapping("/update")
    public String updateCount(@RequestParam Long dishId,
                              @RequestParam int delta,
                              Authentication auth,
                              @CookieValue(value = "cart_items", defaultValue = "") String cartCookie,
                              HttpServletResponse response) {
        String email = (auth != null) ? auth.getName() : null;
        log.info("Updating quantity for dish ID: {} for user: {}", dishId, email);
        cartService.updateQuantity(dishId, delta, email, cartCookie, response);
        return "redirect:/basket";
    }
}