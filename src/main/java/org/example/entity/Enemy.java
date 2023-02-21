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
public class Enemy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "enemy_name", unique = true, nullable = false)
    private String name;

    @Column(name = "real_name", unique = true, nullable = false)
    private String realName;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "enemy")
    private Superhero superhero;

    public Enemy(String name, String realName) {
        this.name = name;
        this.realName = realName;
    }

}
