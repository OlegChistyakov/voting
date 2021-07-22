package ru.graduation.voting.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.voting.model.Restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class RestaurantRepository {

    @PersistenceContext
    private EntityManager em;

    public Restaurant getWithMenuByDate(Integer id, LocalDate date) {
        return em.createQuery(
                "SELECT r FROM Restaurant r " +
                        "JOIN FETCH r.menu m " +
                        "WHERE r.id=:id AND m.date=:date", Restaurant.class)
                .setParameter("id", id)
                .setParameter("date", date)
                .getSingleResult();
    }

    public Restaurant getWithAllMenuBetweenDate(Integer id, LocalDate startDate, LocalDate endDate) {
        return em.createQuery(
                "SELECT r FROM Restaurant r " +
                        "JOIN FETCH r.menu m " +
                        "WHERE r.id=:id AND m.date>=:startDate AND m.date<=:endDate", Restaurant.class)
                .setParameter("id", id)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getSingleResult();
    }

    public List<Restaurant> getAllWithMenuByDate(LocalDate date) {
        List<Restaurant> restaurants = em
                .createQuery(
                        "SELECT DISTINCT r FROM Restaurant r " +
                                "JOIN FETCH r.menu m WHERE m.date=:date", Restaurant.class)
                .setParameter("date", date)
                .getResultList();
        return restaurants;
    }
}
