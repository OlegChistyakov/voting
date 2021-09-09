package ru.graduation.voting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurants")
public class Restaurant extends NamedEntity {

    @NotBlank
    @Size(min = 5, max = 100)
    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Dish> menu;

    public Restaurant(Integer id, String name, String address, List<Dish> menu) {
        super(id, name);
        this.address = address;
        this.menu = menu;
    }
}