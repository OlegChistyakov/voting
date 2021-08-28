package ru.graduation.voting.web;

import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;
import ru.graduation.voting.model.User;

@Getter
@ToString(of = "user")
public class AuthUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public AuthUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }

    public User getUser() {
        return user;
    }
}
