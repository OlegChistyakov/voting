package ru.graduation.voting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DishTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 100)
    String description;

    @NotNull
    @Min(10)
    @Max(5000)
    int price;

    @NotNull
    LocalDate date;
}