package ru.graduation.voting.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "restaurants")
public class Restaurant extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Dish> menu;

    public Restaurant(Integer id, String name, String address, List<Dish> menu) {
        super(id);
        this.name = name;
        this.address = address;
        this.menu = menu;
    }

    public Restaurant(String name, String address, List<Dish> menu) {
        this(null, name, address, menu);
    }
}