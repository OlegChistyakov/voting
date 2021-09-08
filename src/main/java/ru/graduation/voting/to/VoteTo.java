package ru.graduation.voting.to;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    RestaurantTo restaurant;

    public VoteTo(Integer id, LocalDate localDate, RestaurantTo restaurant) {
        super(id);
        this.localDate = localDate;
        this.restaurant = restaurant;
    }
}