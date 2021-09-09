package ru.graduation.voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "votes", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"user_id", "date"}, name = "votes_unique_date_user_idx")})
public class Vote extends BaseEntity {

    @Column(name = "date")
    private LocalDate localDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rest_id")
    private Restaurant restaurant;

    public Vote(Integer id, LocalDate localDate, User user, Restaurant restaurant) {
        super(id);
        this.localDate = localDate;
        this.user = user;
        this.restaurant = restaurant;
    }
}