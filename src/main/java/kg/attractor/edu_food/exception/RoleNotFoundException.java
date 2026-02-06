package kg.attractor.edu_food.exception;

import java.util.NoSuchElementException;

public class RoleNotFoundException extends NoSuchElementException {
    public RoleNotFoundException(){super("Роль не найдена");}
}
