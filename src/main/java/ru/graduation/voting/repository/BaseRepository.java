package ru.graduation.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.voting.error.NotFoundException;

import static ru.graduation.voting.util.ValidationUtil.checkModification;
import static ru.graduation.voting.web.GlobalExceptionHandler.EXCEPTION_NOT_EXIST_ENTITY;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Integer> {

    //    https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query.spel-expressions
    @Transactional
    @Modifying
    @Query("DELETE FROM #{#entityName} u WHERE u.id=:id")
    int delete(@Param("id") int id);

    default void deleteExisted(int id) {
        checkModification(delete(id), id);
    }

    default T findExist(int id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException(EXCEPTION_NOT_EXIST_ENTITY));
    }
}
