package ru.graduation.voting.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Optional<Restaurant> getByNameIgnoreCase(String name);

    @Query("SELECT r FROM Restaurant r WHERE r.id=?1")
    @EntityGraph(attributePaths = {"menu"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Restaurant> getWithMenu(Integer restId);

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu m WHERE r.id=?1 AND m.date>=?2 AND m.date<=?3")
    @EntityGraph(attributePaths = {"menu"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Restaurant> getWithMenuBetweenDate(Integer restId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu m WHERE m.date>=?1 AND m.date<=?2")
    @EntityGraph(attributePaths = {"menu"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<List<Restaurant>> getAllWithMenuBetweenDate(LocalDate startDate, LocalDate endDate);

    @Modifying
    @Transactional
    @Query("DELETE FROM Restaurant r WHERE r.id=?1 AND r.owner.id=?2")
    void delete(int id, int userId);
}