package kg.attractor.edu_food.service.impl;

import kg.attractor.edu_food.dto.UserDto;
import kg.attractor.edu_food.dto.UserRegistrationDto;
import kg.attractor.edu_food.exception.RoleNotFoundException;
import kg.attractor.edu_food.exception.UserAlreadyExistsException;
import kg.attractor.edu_food.exception.UserNotFoundException;
import kg.attractor.edu_food.model.Role;
import kg.attractor.edu_food.model.User;
import kg.attractor.edu_food.repository.RoleRepository;
import kg.attractor.edu_food.repository.UserRepository;
import kg.attractor.edu_food.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public long registerUser(UserRegistrationDto dto){
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        Role role = roleRepository.findByRole("USER")
                .orElseThrow(RoleNotFoundException::new);

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode( dto.getPassword()))
                .role(role)
                .enabled(true)
                .build();
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        return mapToDto(user);
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .roleId(user.getRole().getId())
                .build();
    }
}