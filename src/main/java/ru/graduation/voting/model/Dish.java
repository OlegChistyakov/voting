package ru.graduation.voting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dishes", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"rest_id", "local_date", "description"}, name = "dishes_unique_rest_date_description_idx")})
public class Dish extends BaseEntity {

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Min(10)
    @Max(5000)
    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "local_date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    public Dish(Integer id, String description, int price, LocalDate date, Restaurant restaurant) {
        super(id);
        this.description = description;
        this.price = price;
        this.date = date;
        this.restaurant = restaurant;
    }
}