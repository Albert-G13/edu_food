package kg.attractor.edu_food.controller;

import kg.attractor.edu_food.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public String createOrder(Authentication auth) {
        if (auth != null) {
            log.info("Attempting to create order for user: {}", auth.getName());
            orderService.createOrder(auth.getName());
            log.info("Order successfully created for user: {}", auth.getName());
            return "redirect:/orders/success";
        }
        return "redirect:/auth/login";
    }
    @GetMapping("/success")
    public String success() {
        return "redirect:/users/profile";
    }
    @GetMapping("/history")
    public String getOrderHistory(Authentication auth, Model model) {
        if (auth == null) {
            log.warn("Guest tried to access order history");
            return "redirect:/auth/login";
        }
        log.info("Fetching order history for user: {}", auth.getName());
        model.addAttribute("orders", orderService.getOrdersByUser(auth.getName()));
        return "order/history";
    }
}
