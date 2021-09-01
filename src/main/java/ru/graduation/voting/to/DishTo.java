package ru.graduation.voting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.graduation.voting.model.Dish;
import ru.graduation.voting.model.Restaurant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DishTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 100)
    String description;

    @NotNull
    int price;

    @NotNull
    LocalDate date;

    public static Dish convert(DishTo to, Restaurant restaurant) {
        return new Dish(to.id, to.description, to.getPrice(), to.getDate(), restaurant);
    }
}