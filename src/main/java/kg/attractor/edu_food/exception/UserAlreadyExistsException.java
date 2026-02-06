package kg.attractor.edu_food.exception;

public class UserAlreadyExistsException extends IllegalArgumentException {
    public UserAlreadyExistsException(){super("Данный email уже зарегистрирован");}
}
