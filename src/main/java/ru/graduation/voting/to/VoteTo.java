package ru.graduation.voting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VoteTo extends BaseTo {
    LocalDate localDate;
    RestaurantTo restaurant;

    public VoteTo(Integer id, LocalDate localDate, RestaurantTo restaurant) {
        super(id);
        this.localDate = localDate;
        this.restaurant = restaurant;
    }
}