package ru.graduation.voting.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.user.id=?1 ORDER BY v.restaurant.id")
    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Vote> getAllByUserId(int id);

    @Query("SELECT v FROM Vote v WHERE v.localDate>=?1 AND v.localDate<=?2 ORDER BY v.localDate DESC")
    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Vote> getAllByBetweenDate(LocalDate starDate, LocalDate endDate);

    Optional<Vote> findByUserIdAndLocalDate(int userId, LocalDate date);

    Optional<Vote> findByIdAndUserId(int id, int userId);

}