package kg.attractor.edu_food.controller;

import jakarta.validation.Valid;
import kg.attractor.edu_food.dto.UserRegistrationDto;
import kg.attractor.edu_food.service.RoleService;
import kg.attractor.edu_food.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        log.info("The new user has successfully started registration");
        model.addAttribute("user", new UserRegistrationDto());
        model.addAttribute("roles", roleService.findAll());
        return "auth/register";
    }
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserRegistrationDto dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors occurred during registration: {}", bindingResult.getAllErrors());
            model.addAttribute("user", dto);
            model.addAttribute("roles", roleService.findAll());
            return "auth/register";
        }

        userService.registerUser(dto);
        log.info("User {} has successfully registered", dto.getEmail());
        model.addAttribute("user", new UserRegistrationDto());
        model.addAttribute("roles", roleService.findAll());
        return "redirect:/auth/login";
    }
    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }
}