package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Power {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Boolean fatality;

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER, mappedBy = "powers")
    private Set<Superhero> superheroes = new HashSet<>();

    public Power(String name, Boolean fatality) {

        this.name = name;
        this.fatality = fatality;
    }

    public void addSuperheroToPower(Superhero superhero) {
        superheroes.add(superhero);
    }

    public void removeSuperheroFromPower(Superhero superhero) {
        superheroes.remove(superhero);
    }
}
