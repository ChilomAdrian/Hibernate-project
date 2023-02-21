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
public class Superhero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String heroName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Enemy enemy;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Sidekick sidekick;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Power> powers = new HashSet<>();

    public void addPowerToSuperhero(Power power) {
        powers.add(power);
    }

    public void removePowerFromSuperhero(Power power) {
        powers.remove(power);
    }
}
