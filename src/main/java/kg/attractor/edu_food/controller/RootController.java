package kg.attractor.edu_food.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class RootController {

    @GetMapping("/")
    public String root() {
        log.info("Redirecting from root to /restaurants");
        return "redirect:/restaurants";
    }
}
