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

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        if (restaurant.isNew()) {
            for (Dish d : restaurant.getMenu()) {
                d.setRestaurant(restaurant);
            }
            em.persist(restaurant);
            return restaurant;
        } else if (get(restaurant.getId()) == null) {
            return null;
        }
        return em.merge(restaurant);
    }

    public Restaurant get(Integer id) {
        return em.find(Restaurant.class, id);
    }

    public Restaurant getWithMenu(Integer restId) {
        return em.createQuery(
                        "SELECT r FROM Restaurant r " +
                                "JOIN FETCH r.menu m " +
                                "WHERE r.id=:id", Restaurant.class)
                .setParameter("id", restId)
                .getSingleResult();
    }

    public Restaurant getWithMe(Integer id) {
        return em.find(Restaurant.class, id);
    }

    public List<Restaurant> getAll() {
        return em.createQuery("SELECT r FROM Restaurant r", Restaurant.class).getResultList();
    }

    public Restaurant getWithMenuByDate(Integer id, LocalDate date) {
        return em.createQuery(
                        "SELECT r FROM Restaurant r " +
                                "JOIN FETCH r.menu m " +
                                "WHERE r.id=:id AND m.date=:date", Restaurant.class)
                .setParameter("id", id)
                .setParameter("date", date)
                .getSingleResult();
    }

    public List<Restaurant> getWithAllMenuBetweenDate(LocalDate startDate, LocalDate endDate) {
        return em.createQuery(
                        "SELECT r FROM Restaurant r " +
                                "JOIN FETCH r.menu m " +
                                "WHERE m.date>=:startDate AND m.date<=:endDate", Restaurant.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
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
