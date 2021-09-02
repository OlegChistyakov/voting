package ru.graduation.voting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.graduation.voting.model.BaseEntity;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VoteTo extends BaseEntity {
    LocalDate localDate;
    RestaurantTo restaurant;

    public VoteTo(Integer id, LocalDate localDate, RestaurantTo restaurant) {
        super(id);
        this.localDate = localDate;
        this.restaurant = restaurant;
    }

    public VoteTo(LocalDate localDate, RestaurantTo restaurant) {
        this.localDate = localDate;
        this.restaurant = restaurant;
    }
}
