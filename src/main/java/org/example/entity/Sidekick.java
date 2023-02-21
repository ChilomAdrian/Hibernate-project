package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Sidekick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String mainPower;
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "sidekick")
    private Set<Superhero> superheroes = new HashSet<>();

    public Sidekick(String name, String mainPower) {
        this.name = name;
        this.mainPower = mainPower;
    }

    public void addSidekickToSuperhero(Superhero superhero) {
        superheroes.add(superhero);
        superhero.setSidekick(this);
    }

    public void removeSidekickToSuperhero(Superhero superhero) {
        superheroes.remove(superhero);
        superhero.setSidekick(this);
    }
}
