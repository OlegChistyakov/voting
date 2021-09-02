package ru.graduation.voting.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Value
public class RestaurantTo extends NamedTo {

    @NotBlank
    @Size(max = 100)
    String address;

    public RestaurantTo(Integer id, String name, String address) {
        super(id, name);
        this.address = address;
    }
}