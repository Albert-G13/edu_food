package kg.attractor.edu_food.service;

import kg.attractor.edu_food.dto.RestaurantDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RestaurantService {

    Page<RestaurantDto> findAll(Pageable page);

    RestaurantDto findById(Long id);

    Page<RestaurantDto> findByName(String name, Pageable page);
}
