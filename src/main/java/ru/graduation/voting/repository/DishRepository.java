package ru.graduation.voting.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.voting.model.Dish;

import java.util.Optional;

import static ru.graduation.voting.util.ValidationUtil.checkModification;

@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant r WHERE d.id=?1 AND r.id=?2")
    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Dish> findByIdWithRestaurant(int id, int restId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d  WHERE d.id=?1 AND d.restaurant.id=?2")
    int delete(int id, int restId);

    default void deleteExisted(int id, int restId) {
        checkModification(delete(id, restId), id);
    }
}