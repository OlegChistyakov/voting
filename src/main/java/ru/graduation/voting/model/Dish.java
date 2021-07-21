package ru.graduation.voting.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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
    private Date date;

    @ManyToOne
    @JoinColumn(name = "rest_id")
    private Restaurant restaurant;
}
