package ru.graduation.voting.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "votes")
public class Vote extends AbstractEntity {

    @Column(name = "date")
    LocalDate localDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "rest_id")
    private Restaurant restaurant;

}