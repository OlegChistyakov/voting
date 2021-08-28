package ru.graduation.voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dishes")
public class Dish extends BaseEntity {

    @Column(name = "description")
    @NotBlank
    @Size(min = 2, max = 100)
    private String description;

    @Column(name = "price")
    @NotNull
    private int price;

    @Column(name = "date")
    @NotNull
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

    public Dish(String description, int price, LocalDate date, Restaurant restaurant) {
        this(null, description, price, date, restaurant);
    }
}