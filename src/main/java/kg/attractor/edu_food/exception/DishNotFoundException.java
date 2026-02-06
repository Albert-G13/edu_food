package kg.attractor.edu_food.exception;

import java.util.NoSuchElementException;

public class DishNotFoundException extends NoSuchElementException {
    public DishNotFoundException() {
        super("Блюдо не найдено");
    }
}
