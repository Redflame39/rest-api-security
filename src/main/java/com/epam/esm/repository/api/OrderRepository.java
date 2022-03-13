package com.epam.esm.repository.api;

import com.epam.esm.model.entity.Order;

import java.util.Optional;

/**
 * The interface Order repository.
 *
 * @param <K> the type parameter
 */
public interface OrderRepository<K> {

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<Order> findById(K id);

    /**
     * Create order.
     *
     * @param order the order
     * @return the order
     */
    Order create(Order order);

}
