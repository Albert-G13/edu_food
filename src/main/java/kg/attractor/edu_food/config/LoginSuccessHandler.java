package kg.attractor.edu_food.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.attractor.edu_food.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final CartService cartService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String cartValue = "";
        if (request.getCookies() != null) {
            cartValue = Arrays.stream(request.getCookies())
                    .filter(c -> "cart_items".equals(c.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse("");
        }

        if (!cartValue.isEmpty()) {
            cartService.mergeCart(authentication.getName(), cartValue);

            Cookie cookie = new Cookie("cart_items", null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

        response.sendRedirect("/restaurants");
    }
}