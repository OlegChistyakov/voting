package ru.graduation.voting.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    Optional<Restaurant> getByNameIgnoreCase(String name);

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu m WHERE r.id=?1 AND m.date>=?2 AND m.date<=?3")
    @EntityGraph(attributePaths = {"menu"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Restaurant> getWithMenuBetweenDate(Integer restId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu m WHERE m.date>=?1 AND m.date<=?2")
    @EntityGraph(attributePaths = {"menu"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Restaurant> getAllWithMenuBetweenDate(LocalDate startDate, LocalDate endDate);

}