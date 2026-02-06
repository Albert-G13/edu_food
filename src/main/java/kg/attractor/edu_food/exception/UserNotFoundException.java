package kg.attractor.edu_food.exception;

import java.util.NoSuchElementException;

public class UserNotFoundException extends NoSuchElementException {
    public UserNotFoundException() {
        super("Пользователь не найден");
    }
}
