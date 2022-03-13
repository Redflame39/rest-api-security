package com.epam.esm.repository.impl;

import com.epam.esm.model.entity.Order;
import com.epam.esm.repository.api.OrderRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Component
public class OrderRepositoryImpl implements OrderRepository<Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    @Transactional
    public Order create(Order order) {
        entityManager.persist(order);
        entityManager.flush();
        return order;
    }
}
