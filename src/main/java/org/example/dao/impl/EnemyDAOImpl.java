package org.example.dao.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.java.Log;
import org.example.dao.EnemyDAO;
import org.example.dao.SuperheroDAO;
import org.example.entity.Enemy;
import org.example.entity.Superhero;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Log
public class EnemyDAOImpl implements EnemyDAO {

    private final SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;
    private final SuperheroDAO superheroDAO = new SuperheroDAOImpl();

    public EnemyDAOImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }


    @Override
    public void insertEnemy(Enemy enemy) {
        openSessionAndTransaction();
        session.persist(enemy);
        commitTransactionAndCloseSession();

    }

    @Override
    public void insertMultipleEnemies(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            insertEnemy(enemy);
        }

    }

    @Override
    public List<Enemy> getAllEnemies() {
        openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Enemy> getAllEnemiesQuery = criteriaBuilder.createQuery(Enemy.class);
        getAllEnemiesQuery.from(Enemy.class);
        List<Enemy> enemyList = session.createQuery(getAllEnemiesQuery).getResultList();
        closeSession();
        return enemyList;
    }

    @Override
    public Enemy getEnemyByIdWithCriteriaBuilder(Integer id) {
        openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Enemy> getAllEnemiesQuery = criteriaBuilder.createQuery(Enemy.class);
        Root<Enemy> from = getAllEnemiesQuery.from(Enemy.class);
        getAllEnemiesQuery.where(criteriaBuilder.equal(from.get("id"), id));
        Enemy enemy = session.createQuery(getAllEnemiesQuery).getSingleResult();
        closeSession();
        return enemy;
    }

    @Override
    public Optional<Enemy> getEnemyById(Integer id) {
        openSession();
        Optional<Enemy> enemy = Optional.ofNullable(session.find(Enemy.class, id));
        closeSession();
        return enemy;
    }

    @Override
    public void deleteEnemyById(Integer id) {
        Optional<Enemy> optionalEnemy = getEnemyById(id);
        if (optionalEnemy.isPresent()) {
            openSessionAndTransaction();
            session.remove(optionalEnemy.get());
        } else
            log.info("Cannot find entity with id=" + id);

    }

    @Override
    public void updateEnemy(Enemy enemyFromFrontend) {
        openSessionAndTransaction();
        session.merge(enemyFromFrontend);
        commitTransactionAndCloseSession();
    }


    private void openSessionAndTransaction() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    private void openSession() {
        session = sessionFactory.openSession();
    }

    private void commitTransactionAndCloseSession() {
        transaction.commit();
        session.close();
    }

    private void closeSession() {
        session.close();
    }
}
