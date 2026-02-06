package kg.attractor.edu_food.service;

import kg.attractor.edu_food.dto.UserDto;
import kg.attractor.edu_food.dto.UserRegistrationDto;


public interface UserService {
    long registerUser(UserRegistrationDto dto);

    UserDto getUserByEmail(String email);
}
