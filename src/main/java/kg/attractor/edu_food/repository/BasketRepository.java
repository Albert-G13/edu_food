package kg.attractor.edu_food.repository;

import kg.attractor.edu_food.model.Basket;
import kg.attractor.edu_food.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findAllByUser(User user);
    Optional<Basket> findByUserAndDishId(User user, Long dishId);
    void deleteAllByUser(User user);
    List<Basket> findAllByUserEmail(String email);
}
