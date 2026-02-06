package kg.attractor.edu_food.exception;

import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.edu_food.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.NoSuchElementException;
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final CartService cartService;

    @ExceptionHandler(Exception.class)
    public String handleAll(Exception e, Model model) {
        log.error("Global error caught: ", e);
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", e.getMessage());
        return "errors/error";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String noHandlerNotFound(Model model, HttpServletRequest request){
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String notFound(Model model, HttpServletRequest request){
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("details", request);
        return "errors/error";
    }
    @ExceptionHandler(exception = DishNotFoundException.class)
    public String dishNotFound(Model model, DishNotFoundException e){
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", e.getMessage());
        return "errors/error";
    }
    @ExceptionHandler(RoleNotFoundException.class)
    public String roleNotFound(Model model, RoleNotFoundException e){
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", e.getMessage());
        return "errors/error";
    }
    @ExceptionHandler(UserNotFoundException.class)
    public String workExperienceDate(UserNotFoundException e,Model model){
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", e.getMessage());
        return "errors/error";
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public String emailAlreadyExists(UserAlreadyExistsException e,Model model){
        model.addAttribute("status", HttpStatus.CONFLICT.value());
        model.addAttribute("reason", e.getMessage());
        return "errors/error";
    }
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(Model model) {
        model.addAttribute("status", HttpStatus.FORBIDDEN.value());
        model.addAttribute("reason", HttpStatus.FORBIDDEN.getReasonPhrase());
        return "errors/error";
    }
    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalState(IllegalStateException e, Model model) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        return "errors/error";
    }

    @ModelAttribute
    public void addCartCount(Model model,
                             Authentication auth,
                             @CookieValue(value = "cart_items", defaultValue = "") String cartCookie) {
        int count = 0;
        if (auth != null) {
            count = cartService.getBasketCountForUser(auth.getName());
        } else {
            count = cartService.getBasketCountFromCookie(cartCookie);
        }
        model.addAttribute("cartCount", count);
    }
}
