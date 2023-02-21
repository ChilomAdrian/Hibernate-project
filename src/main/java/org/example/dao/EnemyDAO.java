package org.example.dao;


import org.example.entity.Enemy;

import java.util.List;
import java.util.Optional;

public interface EnemyDAO {

    void insertEnemy(Enemy enemy);

    void insertMultipleEnemies(List<Enemy> enemy);

    List<Enemy> getAllEnemies();

    Optional<Enemy> getEnemyById(Integer id);

    void deleteEnemyById(Integer id);

    void updateEnemy(Enemy enemyFromFrontend);

    Enemy getEnemyByIdWithCriteriaBuilder(Integer id);

}
