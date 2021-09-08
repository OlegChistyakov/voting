package ru.graduation.voting.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.graduation.voting.error.NotFoundException;

import java.util.Optional;
import java.util.function.Function;

import static ru.graduation.voting.web.GlobalExceptionHandler.EXCEPTION_NOT_EXIST_ENTITY;

@UtilityClass
public class ControllerUtil {
    public static <T> ResponseEntity<T> formResponse(Optional<T> found) {
        T t = unpack(found);
        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    public static <T, R> ResponseEntity<R> formResponse(Optional<T> found, Class<R> cls, Function<T, R> converter) {
        T t = unpack(found);
        R r = cls.cast(converter.apply(t));
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    public static <T> T unpack(Optional<T> found) {
        return found.orElseThrow(() -> new NotFoundException(EXCEPTION_NOT_EXIST_ENTITY));
    }
}