package kg.attractor.edu_food.controller;

import kg.attractor.edu_food.service.OrderService;
import kg.attractor.edu_food.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/profile")
    public String showProfile(Model model, Authentication auth) {
        log.info("User {} is accessing profile page", auth.getName());
        model.addAttribute("user", userService.getUserByEmail(auth.getName()));
        model.addAttribute("orders", orderService.getOrdersByUser(auth.getName()));
        return "user/profile";
    }
}