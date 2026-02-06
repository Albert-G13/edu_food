package kg.attractor.edu_food.service.impl;

import kg.attractor.edu_food.dto.DishDto;
import kg.attractor.edu_food.model.Dish;
import kg.attractor.edu_food.repository.DishRepository;
import kg.attractor.edu_food.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;

    @Override
    public Page<DishDto> findAllByRestaurantId(Long id, Pageable page){
        return dishRepository.findAllByRestaurantId(id, page).map(this::mapToDto);
    }
    private DishDto mapToDto(Dish dish) {
        return DishDto.builder()
                .id(dish.getId())
                .name(dish.getName())
                .price(dish.getPrice())
                .build();
    }
}
