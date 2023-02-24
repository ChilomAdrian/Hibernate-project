package org.example;

import com.github.javafaker.Faker;
import org.example.dao.EnemyDAO;
import org.example.dao.SuperheroDAO;
import org.example.dao.impl.EnemyDAOImpl;
import org.example.dao.impl.SuperheroDAOImpl;
import org.example.entity.Enemy;
import org.example.entity.Power;
import org.example.entity.Sidekick;
import org.example.entity.Superhero;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        SuperheroDAO superheroDAO = new SuperheroDAOImpl();
        // superheroDAO.insertSuperhero(getRandomSuperhero());
        superheroDAO.insertMultipleSuperheroes(get10RandomHeroes());

    }

    private static Superhero getRandomSuperhero() {
        Faker faker = new Faker();
        Random random = new Random();
        Superhero superhero = new Superhero();
        superhero.setName(faker.funnyName().name());
        superhero.setHeroName(faker.superhero().name());
        superhero.setEnemy(new Enemy(faker.superhero().name(), faker.name().fullName()));
        superhero.setSidekick(new Sidekick(faker.name().lastName(), faker.superhero().power()));
        //superhero.addPowerToSuperhero(new Power(faker.superhero().power(), random.nextBoolean()));
        superhero.setPowers(generateRandomPowers(superhero));
        return superhero;
    }

    public static Set<Power> generateRandomPowers(Superhero superhero) {
        Faker faker = new Faker();
        Random random = new Random();
        Set<Power> powers = new HashSet<>();
        // for (int i = 0; i < random.nextInt(3); i++) {
        Power power = new Power(faker.superhero().power(), random.nextBoolean());
        power.addSuperheroToPower(superhero);
        powers.add(power);
        //}
        return powers;
    }

    private static List<Superhero> get10RandomHeroes() {
        return Stream.generate(Main::getRandomSuperhero).limit(10).toList();
    }

}