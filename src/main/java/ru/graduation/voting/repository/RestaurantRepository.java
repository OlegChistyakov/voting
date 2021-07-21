package ru.graduation.voting.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.voting.model.Dish;
import ru.graduation.voting.model.Restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

@Repository
@Transactional(readOnly = true)
public class RestaurantRepository {

    @PersistenceContext
    private EntityManager em;

    public Restaurant getWithMenuByDate(Integer id, LocalDate date) {
        Restaurant restaurant = em.createQuery("SELECT r FROM Restaurant r WHERE r.id=:id", Restaurant.class)
                .setParameter("id", id)
                .getSingleResult();
        restaurant.setMenu(em.createQuery("SELECT d FROM Dish d WHERE d.restaurant.id = :rest_id AND d.date=:date", Dish.class)
                .setParameter("rest_id", restaurant.getId())
                .setParameter("date", date)
                .getResultList());
        return restaurant;
    }
}
