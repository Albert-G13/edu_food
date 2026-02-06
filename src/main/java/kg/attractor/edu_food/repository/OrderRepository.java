package kg.attractor.edu_food.repository;

import kg.attractor.edu_food.model.Order;
import kg.attractor.edu_food.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserOrderByOrderDateDesc(User user);
}
