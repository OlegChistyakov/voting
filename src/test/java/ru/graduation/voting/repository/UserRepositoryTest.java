package ru.graduation.voting.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.graduation.voting.model.User;

@SpringBootTest
@Sql(scripts = "classpath:db/popDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Test
    void get() {
        User user = repository.findById(100_000).get();
        System.out.println(user);
    }
}
