package kg.attractor.edu_food.service;

import kg.attractor.edu_food.dto.DishDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DishService {
    Page<DishDto> findAllByRestaurantId(Long id, Pageable page);
}
