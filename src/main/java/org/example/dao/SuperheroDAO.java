package org.example.dao;

import org.example.entity.Enemy;
import org.example.entity.Superhero;

import java.util.List;
import java.util.Optional;

public interface SuperheroDAO {

    Superhero insertSuperhero(Superhero superhero);

    void insertMultipleSuperheroes(List<Superhero> superheroes);

    List<Superhero> getAllSuperheroes();

    Optional<Superhero> getSuperheroById(Integer id);

    void deleteSuperheroById(Integer id);

    void updateSuperhero(Superhero superheroFromFrontend);

    Superhero getSuperheroByIdWithCriteriaBuilder(Integer id);
}
