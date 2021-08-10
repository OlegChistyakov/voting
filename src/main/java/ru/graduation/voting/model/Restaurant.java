package ru.graduation.voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "restaurants")
public class Restaurant extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Dish> menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User owner;

    public Restaurant(Integer id, String name, String address, List<Dish> menu, User owner) {
        super(id);
        this.name = name;
        this.address = address;
        this.menu = menu;
        this.owner = owner;
    }

    public Restaurant(String name, String address, List<Dish> menu, User owner) {
        this(null, name, address, menu, owner);
    }
}