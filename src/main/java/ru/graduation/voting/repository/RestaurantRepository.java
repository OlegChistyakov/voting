package ru.graduation.voting.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.voting.model.Dish;
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
        Restaurant restaurant = getRestaurantById(id);
        restaurant.setMenu(em.createQuery(
                "SELECT d FROM Dish d WHERE d.restaurant.id = :rest_id AND d.date=:date", Dish.class)
                .setParameter("rest_id", restaurant.getId())
                .setParameter("date", date)
                .getResultList());
        return restaurant;
    }

    public Restaurant getWithAllMenuBetweenDate(Integer id, LocalDate startDate, LocalDate endDate) {
        Restaurant restaurant = getRestaurantById(id);
        restaurant.setMenu(em.createQuery(
                "SELECT d FROM Dish d WHERE d.restaurant.id = :rest_id AND d.date>=:startDate AND d.date<=:endDate", Dish.class)
                .setParameter("rest_id", restaurant.getId())
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList());
        return restaurant;
    }

    public List<Restaurant> getAllWithMenuByDate(LocalDate date) {
        List<Restaurant> restaurants = em
                .createQuery("SELECT DISTINCT r FROM Restaurant r JOIN FETCH r.menu m WHERE m.date=:date", Restaurant.class)
                .setParameter("date", date)
                .getResultList();
        return restaurants;
    }

    private Restaurant getRestaurantById(Integer id) {
        return em.createQuery("SELECT r FROM Restaurant r WHERE r.id=:id", Restaurant.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
