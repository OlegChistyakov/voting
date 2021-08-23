package ru.graduation.voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Restaurant extends AbstractEntity {

    @Column(name = "name")
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @Column(name = "address")
    @NotBlank
    @Size(min = 5, max = 100)
    private String address;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Dish> menu;

    public Restaurant(Integer id, String name, String address, List<Dish> menu) {
        super(id);
        this.name = name;
        this.address = address;
        this.menu = menu;
    }
}