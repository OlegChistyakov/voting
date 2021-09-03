package ru.graduation.voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dishes", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"rest_id", "date", "description"}, name = "dishes_unique_rest_date_description_idx")})
public class Dish extends BaseEntity {

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id")
    @JsonBackReference
    private Restaurant restaurant;

    public Dish(Integer id, String description, int price, LocalDate date, Restaurant restaurant) {
        super(id);
        this.description = description;
        this.price = price;
        this.date = date;
        this.restaurant = restaurant;
    }
}