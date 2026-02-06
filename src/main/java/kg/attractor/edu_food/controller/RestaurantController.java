package kg.attractor.edu_food.controller;

import kg.attractor.edu_food.service.DishService;
import kg.attractor.edu_food.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final DishService dishService;

    @GetMapping
    public String findAll(
            @PageableDefault(size = 5) Pageable page,
            @RequestParam(name = "search", required = false) String search,
            Model model) {
        if (search != null && !search.isBlank()) {
            log.info("Searching for restaurants with query: '{}' (page: {})", search, page.getPageNumber());
            model.addAttribute("restaurants", restaurantService.findByName(search, page));
        } else {
            log.info("Fetching all restaurants (page: {})", page.getPageNumber());
            model.addAttribute("restaurants", restaurantService.findAll(page));
        }
        model.addAttribute("search", search);
        return "restaurant/restaurants";
    }
    @GetMapping("/{id}")
    public String findById(@PageableDefault(size = 10) Pageable pageable, Model model, @PathVariable Long id) {
        log.info("Opening restaurant page ID: {}", id);
        model.addAttribute("restaurant", restaurantService.findById(id));
        model.addAttribute("dishes", dishService.findAllByRestaurantId(id, pageable));
        return "restaurant/restaurant";
    }
}
