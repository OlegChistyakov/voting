package ru.graduation.voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dishes")
public class Dish extends AbstractEntity {

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id")
    //@JsonBackReference
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