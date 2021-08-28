package ru.graduation.voting.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.graduation.voting.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {

    Optional<User> getByEmail(String email);

}