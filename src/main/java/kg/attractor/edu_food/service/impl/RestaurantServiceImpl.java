package kg.attractor.edu_food.service.impl;

import kg.attractor.edu_food.dto.RestaurantDto;
import kg.attractor.edu_food.model.Restaurant;
import kg.attractor.edu_food.repository.RestaurantRepository;
import kg.attractor.edu_food.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Override
    public Page<RestaurantDto> findAll(Pageable page){
        return restaurantRepository.findAll(page).map(this::mapToDto);
    }

    @Override
    public RestaurantDto findById(Long id) {
        return restaurantRepository.findById(id).map(this::mapToDto).orElseThrow();
    }

    @Override
    public Page<RestaurantDto> findByName(String name, Pageable page){
        return restaurantRepository.findByNameContainingIgnoreCase(name, page).map(this::mapToDto);
    }

    private RestaurantDto mapToDto(Restaurant restaurant) {
        return RestaurantDto.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .build();
    }
}
