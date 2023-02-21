package org.example.dao.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.java.Log;
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
public class SuperheroDAOImpl implements SuperheroDAO {

    private final SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;


    public SuperheroDAOImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public Superhero insertSuperhero(Superhero superhero) {
        openSessionAndTransaction();
        Superhero persistedSuperhero = session.merge(superhero);
        commitTransactionAndCloseSession();
        return persistedSuperhero;
    }

    @Override
    public void insertMultipleSuperheroes(List<Superhero> superheroes) {
        for (Superhero superhero : superheroes) {
            insertSuperhero(superhero);
        }
    }

    @Override
    public List<Superhero> getAllSuperheroes() {
        openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Superhero> getAllSuperheroesQuery = criteriaBuilder.createQuery(Superhero.class);
        getAllSuperheroesQuery.from(Superhero.class);
        List<Superhero> superheroList = session.createQuery(getAllSuperheroesQuery).getResultList();
        closeSession();
        return superheroList;
    }

    @Override
    public Optional<Superhero> getSuperheroById(Integer id) {
        openSession();
        Optional<Superhero> superhero = Optional.ofNullable(session.find(Superhero.class, id));
        closeSession();
        return superhero;
    }

    @Override
    public void deleteSuperheroById(Integer id) {
        Optional<Superhero> optionalSuperhero = getSuperheroById(id);
        if (optionalSuperhero.isPresent()) {
            openSessionAndTransaction();
            session.remove(optionalSuperhero.get());
        } else
            log.info("Cannot find entity with id=" + id);

    }

    @Override
    public void updateSuperhero(Superhero superheroFromFrontend) {
        openSessionAndTransaction();
        session.merge(superheroFromFrontend);
        commitTransactionAndCloseSession();
    }

    @Override
    public Superhero getSuperheroByIdWithCriteriaBuilder(Integer id) {
        openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Superhero> getAllSuperherosQuery = criteriaBuilder.createQuery(Superhero.class);
        Root<Superhero> from = getAllSuperherosQuery.from(Superhero.class);
        getAllSuperherosQuery.where(criteriaBuilder.equal(from.get("id"), id));
        Superhero superhero = session.createQuery(getAllSuperherosQuery).getSingleResult();
        closeSession();
        return superhero;
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
